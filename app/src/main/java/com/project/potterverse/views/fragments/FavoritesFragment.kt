package com.project.potterverse.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.Adapter.FavMovieAdapter
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentFavoritesBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.MovieDetailsActivity


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: FavMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = FavMovieAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.movieFavRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }

        observeMovieFav()
        onFavMovieClick()
    }

    private fun onFavMovieClick() {
        movieAdapter.onItemClick = { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra(homeFragment.movieID, movie.id)
            intent.putExtra(homeFragment.movieTitle, movie.attributes.title)
            intent.putExtra(homeFragment.movieImage, movie.attributes.poster)
            intent.putExtra(homeFragment.movieDate, movie.attributes.release_date)
            intent.putExtra(homeFragment.movieRating, movie.attributes.rating)
            intent.putExtra(homeFragment.movieBo, movie.attributes.box_office)
            intent.putExtra(homeFragment.movieDirector, movie.attributes.directors?.get(0))
            startActivity(intent)
        }
    }

    private fun observeMovieFav() {
        viewModel.observerFavMovieLiveData().observe(requireActivity()){ movie ->
            movieAdapter.setFavMovies(movie as ArrayList<MovieDetailData>)
        }

    }

}