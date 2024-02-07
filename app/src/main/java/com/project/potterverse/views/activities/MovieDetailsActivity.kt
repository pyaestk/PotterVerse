package com.project.potterverse.views.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ActivityMovieDetailsBinding
import com.project.potterverse.room.movieDb.MovieDatabase
import com.project.potterverse.viewModel.MovieDetailViewModelFactory
import com.project.potterverse.viewModel.MovieDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var moviePoster: String
    private lateinit var movieId: String
    private lateinit var movieTitle: String
    private lateinit var movieLink: String
    private var isSaved: Boolean? = null


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
        movieTitle = intent.getStringExtra(homeFragment.movieTitle)!!
        binding.Title.text = movieTitle
        binding.movieRating.text = " Rating: ${intent.getStringExtra(homeFragment.movieRating)}"
        binding.movieBoxOffice.text = " Box office: ${intent.getStringExtra(homeFragment.movieBo)}"
        binding.movieReleaseDate.text = " Release Date: ${intent.getStringExtra(homeFragment.movieDate)}"
        binding.movieDirector.text = " Directed By: ${intent.getStringExtra(homeFragment.movieDirector)}"

        moviePoster = intent.getStringExtra(homeFragment.movieImage)!!

        val resourceId = when(movieTitle) {
            "Harry Potter and the Philosopher's Stone" -> R.drawable.h1
            "Harry Potter and the Chamber of Secrets" -> R.drawable.h2
            "Harry Potter and the Prisoner of Azkaban" -> R.drawable.h3
            "Harry Potter and the Goblet of Fire" -> R.drawable.h4
            "Harry Potter and the Order of the Phoenix" -> R.drawable.h5
            "Harry Potter and the Half-Blood Prince" -> R.drawable.h6
            "Harry Potter and the Deathly Hallows - Part 1" -> R.drawable.h7
            "Harry Potter and the Deathly Hallows – Part 2" -> R.drawable.h8
            "Fantastic Beasts and Where to Find Them" -> R.drawable.f1
            "Fantastic Beasts: The Crimes of Grindelwald" -> R.drawable.f2
            "Fantastic Beasts: The Secrets of Dumbledore" -> R.drawable.f3
            else -> 0
        }
        if (resourceId != 0) {
            binding.moviePoster.setImageResource(resourceId)
        }

        binding.backButton.setOnClickListener {
             finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieLink)))
        }

        viewModel.fetchMovieDetails(movieId)
        observerMovieDetails()
        onBookMarkedClick()


    }

    private var movieToSave: MovieDetailData? = null
    private fun onBookMarkedClick() {

        binding.btnBookmark.setOnClickListener {
            movieToSave?.let { movieDetailData ->
                if (isSaved == true) {
                    // Movie is already saved, delete it
                    viewModel.deleteMovies(movieDetailData)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
                    Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show()
                } else {
                    // Movie is not saved, insert it
                    movieDetailData.bookmark = false
                    viewModel.insertMovie(movieDetailData)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observerMovieDetails() {
        viewModel.getMovieDetailsLiveData().observe(this) { movieDetail ->
            movieToSave = movieDetail
            binding.tvSummary.text = movieDetail.attributes.summary
            movieLink = movieDetail.attributes.wiki!!
        }

        viewModel.getAllMovies().observe(this) { movieList ->
            isSaved = movieList.any { it.id == movieId }
            if (isSaved == true) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
            }
            onBookMarkedClick()
        }
    }

}