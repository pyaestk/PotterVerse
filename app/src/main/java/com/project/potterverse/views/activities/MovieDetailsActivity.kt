package com.project.potterverse.views.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.project.potterverse.databinding.ActivityMovieDetailsBinding
import com.project.potterverse.viewModel.MovieDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var moviePoster: String
    private lateinit var movieId: String
    private lateinit var movieLink: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        val intent = intent
        movieId = intent.getStringExtra(homeFragment.movieID)!!
        binding.Title.text = intent.getStringExtra(homeFragment.movieTitle)
        binding.movieRating.text = " Rating: ${intent.getStringExtra(homeFragment.movieRating)}"
        binding.movieBoxOffice.text = " Box office: ${intent.getStringExtra(homeFragment.movieBo)}"
        binding.movieReleaseDate.text = " Release Date: ${intent.getStringExtra(homeFragment.movieDate)}"
        binding.movieDirector.text = " Directed By: ${intent.getStringExtra(homeFragment.movieDirector)}"

        moviePoster = intent.getStringExtra(homeFragment.movieImage)!!
        Glide.with(applicationContext).load(moviePoster).into(binding.moviePoster)

        viewModel.fetchMovieDetails(movieId)
        observerMovieDetails()
        binding.backButton.setOnClickListener {
             finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieLink)))
        }
    }

    private fun observerMovieDetails() {
        viewModel.getMovieDetailsLiveData().observe(this) { movieDetail ->
            binding.tvSummary.text = movieDetail.attributes.summary
            movieLink = movieDetail.attributes.wiki
        }
    }
}