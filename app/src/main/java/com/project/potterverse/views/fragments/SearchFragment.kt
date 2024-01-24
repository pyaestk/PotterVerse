package com.project.potterverse.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.Adapter.BaseBookAdapter
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.Adapter.SearchItem
import com.project.potterverse.Adapter.SearchItemAdapter
import com.project.potterverse.databinding.FragmentSearchBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.viewModel.SearchViewModel
import com.project.potterverse.views.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var itemAdapter: SearchItemAdapter
    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        itemAdapter = SearchItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemResultRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = itemAdapter
        }

        viewModel.observeItemListLiveData().observe(viewLifecycleOwner) { item ->
            itemAdapter.setItems(item as List<SearchItem>)
        }


        var searchJob: Job? = null
        binding.searchbar.addTextChangedListener { searchQuery ->

            if (searchQuery.isNullOrBlank()){
                binding.itemResultRecycler.visibility = View.GONE
            }  else {
                binding.itemResultRecycler.visibility = View.VISIBLE
            }
           searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(200)
                viewModel.getBooks(searchQuery.toString())
                viewModel.getMovies(searchQuery.toString())
                viewModel.searchCharacters(searchQuery.toString())

            }
        }

    }
}

