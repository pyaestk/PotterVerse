package com.project.potterverse.views.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.databinding.ActivityBookDetailsBinding
import com.project.potterverse.room.bookDb.BookDatabase
import com.project.potterverse.utils.Constant
import com.project.potterverse.viewModel.BookDetailViewModelFactory
import com.project.potterverse.viewModel.BookDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var viewModel: BookDetailsViewModel

    private lateinit var bookId: String
    private lateinit var bookImage: String

    private lateinit var bookLink: String
    private var isSaved: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgressBar()

        val bookDatabase = BookDatabase.getInstance(this)
        val viewModelFactory = BookDetailViewModelFactory(bookDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[BookDetailsViewModel::class.java]

        val intent = intent

        bookId = intent.getStringExtra(Constant.bookID)!!

        binding.bookTitle.text = intent.getStringExtra(Constant.bookTitle)
        binding.bookReleaseDate.text = " Release Date: ${intent.getStringExtra(Constant.bookDate)}"
        binding.author.text = " Author: ${intent.getStringExtra(Constant.bookAuthor)}"
        binding.bookchapter.text = " Chapters: ${intent.getStringExtra(Constant.bookChapter)}"

        bookImage = intent.getStringExtra(Constant.bookImage)!!
        Glide.with(applicationContext).load(bookImage).into(binding.bookImageView)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bookLink)))
        }

        viewModel.fetchBookDetails(bookId)
        observerBookDetails()
        onBookmarekdClick()
    }

    private var bookToSave: BookDetailsData? = null
    private fun onBookmarekdClick() {
        binding.btnBookmark.setOnClickListener {
           bookToSave?.let { book ->
               if (isSaved == true){
                   viewModel.deleteBook(book)
                   binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
                   Toast.makeText(this, "Removed from Bookmark section", Toast.LENGTH_SHORT).show()
               } else {
                   book.bookmark = false
                   viewModel.insertBook(book)
                   binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
                   Toast.makeText(this, "Added to Bookmark section", Toast.LENGTH_SHORT).show()
               }
           }
        }
    }

    private fun observerBookDetails() {
        viewModel.getBookDetailsLiveData().observe(this) { bookDetail ->
            bookToSave = bookDetail
            binding.tvSummary.text = bookDetail.attributes.summary
            bookLink = bookDetail.attributes.wiki!!
            hideProgressBar()
        }

        viewModel.getAllBooks().observe(this) { bookList ->
            isSaved = bookList.any { it.id == bookId}
            if (isSaved == true) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
            }
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