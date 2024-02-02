package com.project.potterverse.views.fragments.subFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.FragmentMoviesBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.MovieDetailsActivity
import com.project.potterverse.views.fragments.homeFragment

class MoviesFragment : Fragment() {

    lateinit var binding: FragmentMoviesBinding
    lateinit var viewModel: MainViewModel
    lateinit var movieAdapter: BaseMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = BaseMovieAdapter(1)
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

        showProgressBar()
        binding.movieRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        viewModel.getMovies()
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) { movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieDetailData>)
            hideProgressBar()
        }

        movieAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(homeFragment.movieID, movie.id)
            intent.putExtra(homeFragment.movieTitle, movie.attributes.title)
            intent.putExtra(homeFragment.movieImage, movie.attributes.poster)
            intent.putExtra(homeFragment.movieDate, movie.attributes.release_date)
            intent.putExtra(homeFragment.movieRating, movie.attributes.rating)
            intent.putExtra(homeFragment.movieBo, movie.attributes.box_office)
            intent.putExtra(homeFragment.movieDirector, movie.attributes.directors!![0])
            startActivity(intent)
        }
    }

    private fun showProgressBar(){
        binding.progressBarMovie.visibility = View.VISIBLE
        binding.movieRecycler.visibility = View.INVISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBarMovie.visibility = View.GONE
        binding.movieRecycler.visibility = View.VISIBLE
    }
}