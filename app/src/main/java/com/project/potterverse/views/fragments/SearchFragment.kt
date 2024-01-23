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
import com.project.potterverse.Adapter.BaseBookAdapter
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.data.BookData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentSearchBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var movieAdapter: BaseMovieAdapter
    lateinit var bookAdapter: BaseBookAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        movieAdapter = BaseMovieAdapter(3)
        bookAdapter = BaseBookAdapter(3)
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

        binding.movieResultRecycelr.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }
        viewModel.getMovies()
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) { movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieData>)
        }

        binding.bookResultRecycelr.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = bookAdapter
        }
        viewModel.getBooks()
        viewModel.getBookListLiveData().observe(viewLifecycleOwner) { book ->
            bookAdapter.setBooks(book as ArrayList<BookData>)
        }

        binding. searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter.filter.filter(newText)
                bookAdapter.filter.filter(newText)
                return true
            }

        })

    }
}

