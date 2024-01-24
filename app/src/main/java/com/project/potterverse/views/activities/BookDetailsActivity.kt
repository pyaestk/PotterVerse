package com.project.potterverse.views.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.databinding.ActivityBookDetailsBinding
import com.project.potterverse.viewModel.BookDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var viewModel: BookDetailsViewModel

    private lateinit var bookId: String
    private lateinit var bookImage: String

    private lateinit var bookLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgressBar()
        
        viewModel = ViewModelProvider(this)[BookDetailsViewModel::class.java]

        val intent = intent

        bookId = intent.getStringExtra(homeFragment.bookID)!!

        binding.bookTitle.text = intent.getStringExtra(homeFragment.bookTitle)
        binding.bookReleaseDate.text = " Release Date: ${intent.getStringExtra(homeFragment.bookDate)}"
        binding.author.text = " Author: ${intent.getStringExtra(homeFragment.bookAuthor)}"
        binding.bookchapter.text = " Chapters: ${intent.getStringExtra(homeFragment.bookChapter)}"

        bookImage = intent.getStringExtra(homeFragment.bookImage)!!
        Glide.with(applicationContext).load(bookImage).into(binding.bookImageView)

        viewModel.fetchBookDetails(bookId)
        observerBookDetails()
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bookLink)))
        }
    }

    private fun observerBookDetails() {
        viewModel.getBookDetailsLiveData().observe(this) { bookDetail ->
            binding.tvSummary.text = bookDetail.attributes.summary
            bookLink = bookDetail.attributes.wiki
            hideProgressBar()
        }
    }

    private fun hideProgressBar() {
        binding.summary.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.summary.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }
}