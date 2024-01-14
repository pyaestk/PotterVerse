package com.project.potterverse.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.BookListAdapter
import com.project.potterverse.Adapter.MovieListsAdapter
import com.project.potterverse.data.BookData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentHomeBinding
import com.project.potterverse.viewModel.MainViewModel

class homeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel
    lateinit var movieAdapter: MovieListsAdapter
    lateinit var bookAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        movieAdapter = MovieListsAdapter()
        bookAdapter = BookListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFragmentLayout.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        //for movies
        binding.MovieRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }
        viewModel.getMovies()
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) {movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieData>)

            binding.homeFragmentLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }

        //for books
        binding.booksRecycler.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }
        viewModel.getBooks()
        viewModel.getBookListLiveData().observe(viewLifecycleOwner) {book ->
            bookAdapter.setBooks(book as ArrayList<BookData>)
        }
    }

}