package com.project.potterverse.views.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ActivityMovieDetailsBinding
import com.project.potterverse.room.MovieDatabase
import com.project.potterverse.viewModel.MovieDetailViewModelFactory
import com.project.potterverse.viewModel.MovieDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var moviePoster: String
    private lateinit var movieId: String
    private lateinit var movieLink: String

    private var isSaved = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDatabase = MovieDatabase.getInstance(this)
        val viewModelFactory = MovieDetailViewModelFactory(movieDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailsViewModel::class.java]

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

        onBookMarkedClick()
    }
    private var movieToSave: MovieDetailData? = null
    private fun onBookMarkedClick() {
        binding.btnBookmark.setOnClickListener {
            movieToSave?.let {
                viewModel.insertMovie(it)
                Toast.makeText(this, "Movie has been bookmarked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerMovieDetails() {
        viewModel.getMovieDetailsLiveData().observe(this) { movieDetail ->
            movieToSave = movieDetail
            binding.tvSummary.text = movieDetail.attributes.summary
            movieLink = movieDetail.attributes.wiki!!
        }
    }
}