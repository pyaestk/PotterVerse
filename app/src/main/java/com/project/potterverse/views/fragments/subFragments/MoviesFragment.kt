package com.project.potterverse.views.fragments.subFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.MovieAdapter
import com.project.potterverse.Adapter.MovieListsAdapter
import com.project.potterverse.R
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentMoviesBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity

class MoviesFragment : Fragment() {

    lateinit var binding: FragmentMoviesBinding
    lateinit var viewModel: MainViewModel
    lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = MovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        viewModel.getMovies()
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) { movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieData>)
        }
    }

}