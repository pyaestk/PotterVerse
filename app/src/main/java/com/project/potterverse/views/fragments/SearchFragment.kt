package com.project.potterverse.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.room.util.query
import com.project.potterverse.Adapter.BaseBookAdapter
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.Adapter.SearchItem
import com.project.potterverse.Adapter.SearchItemAdapter
import com.project.potterverse.databinding.FragmentSearchBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.viewModel.SearchViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.BookDetailsActivity
import com.project.potterverse.views.activities.CharacterDetailsActivity
import com.project.potterverse.views.activities.MovieDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var itemAdapter: SearchItemAdapter
    lateinit var viewModel: SearchViewModel
    private val handler = Handler(Looper.getMainLooper())
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

        binding.itemResultRecycler.visibility = View.GONE

        viewModel.observeItemListLiveData().observe(viewLifecycleOwner) { item ->
            binding.itemResultRecycler.visibility = View.VISIBLE
            itemAdapter.setItems(item as List<SearchItem>)

        }

        binding.searchbar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()){
                    viewModel.searchCharacters(query)
                    viewModel.getMovies(query)
                    viewModel.getBooks(query)

                    binding.itemResultRecycler.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {
                    binding.itemResultRecycler.visibility = View.GONE
                }
                return true
            }
        })

        itemAdapter.onItemClick = { selectedItem ->
            when (selectedItem) {
                is SearchItem.Book -> {
                    val intent = Intent(activity, BookDetailsActivity::class.java)
                    intent.putExtra(homeFragment.bookID, selectedItem.data.id)
                    intent.putExtra(homeFragment.bookAuthor, selectedItem.data.attributes.author)
                    intent.putExtra(homeFragment.bookImage, selectedItem.data.attributes.cover)
                    intent.putExtra(
                        homeFragment.bookDate,
                        selectedItem.data.attributes.release_date
                    )
                    intent.putExtra(homeFragment.bookTitle, selectedItem.data.attributes.title)
                    intent.putExtra(
                        homeFragment.bookChapter,
                        selectedItem.data.relationships.chapters.data.size.toString()
                    )
                    startActivity(intent)
                }

                is SearchItem.Movie -> {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    intent.putExtra(homeFragment.movieID, selectedItem.data.id)
                    intent.putExtra(homeFragment.movieTitle, selectedItem.data.attributes.title)
                    intent.putExtra(homeFragment.movieImage, selectedItem.data.attributes.poster)
                    intent.putExtra(
                        homeFragment.movieDate,
                        selectedItem.data.attributes.release_date
                    )
                    intent.putExtra(homeFragment.movieRating, selectedItem.data.attributes.rating)
                    intent.putExtra(homeFragment.movieBo, selectedItem.data.attributes.box_office)
                    intent.putExtra(
                        homeFragment.movieDirector,
                        selectedItem.data.attributes.directors!![0]
                    )
                    startActivity(intent)
                }

                is SearchItem.Character -> {
                    val intent = Intent(activity, CharacterDetailsActivity::class.java)
                    intent.putExtra(homeFragment.chrId, selectedItem.data.id)
                    intent.putExtra(homeFragment.chrName, selectedItem.data.attributes.name)
                    intent.putExtra(homeFragment.chrImage, selectedItem.data.attributes.image)
                    intent.putExtra(homeFragment.chrSpecies, selectedItem.data.attributes.species)
                    intent.putExtra(homeFragment.chrGender, selectedItem.data.attributes.gender)

                    startActivity(intent)
                }
            }

        }

    }
}

