package com.project.potterverse.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.Adapter.BaseBookAdapter
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.Adapter.SearchResultsAdapter
import com.project.potterverse.data.BookData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentSearchBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.viewModel.SearchViewModel
import com.project.potterverse.views.MainActivity


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var itemAdapter: SearchResultsAdapter
    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        itemAdapter = SearchResultsAdapter()
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

        viewModel.getBooks()
        viewModel.getMovies()

        viewModel.observeItemListLiveData().observe(viewLifecycleOwner) { item ->
            itemAdapter.setItems(item as ArrayList<Any>)
        }

        binding. searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemAdapter.filter.filter(newText)
                return true
            }

        })

    }
}

