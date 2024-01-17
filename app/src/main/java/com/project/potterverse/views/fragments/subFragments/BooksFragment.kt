package com.project.potterverse.views.fragments.subFragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.project.potterverse.Adapter.BookAdapter
import com.project.potterverse.Adapter.BookListAdapter
import com.project.potterverse.R
import com.project.potterverse.data.BookData
import com.project.potterverse.databinding.FragmentBooksBinding
import com.project.potterverse.databinding.FragmentHomeBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.BookDetailsActivity
import com.project.potterverse.views.fragments.homeFragment

class BooksFragment : Fragment() {

    lateinit var binding: FragmentBooksBinding
    lateinit var bookAdapter: BookAdapter
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        bookAdapter = BookAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressBar()
        binding.bookRecycler.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            adapter = bookAdapter

            bookAdapter.onItemClick = { book ->
                val intent = Intent(activity, BookDetailsActivity::class.java)
                intent.putExtra(homeFragment.bookID, book.id)
                intent.putExtra(homeFragment.bookAuthor, book.attributes.author)
                intent.putExtra(homeFragment.bookImage, book.attributes.cover)
                intent.putExtra(homeFragment.bookDate, book.attributes.release_date)
                intent.putExtra(homeFragment.bookTitle, book.attributes.title)
                intent.putExtra(homeFragment.bookChapter, book.relationships.chapters.data.size.toString())
                startActivity(intent)
            }
        }

        viewModel.getBooks()
        viewModel.getBookListLiveData().observe(viewLifecycleOwner) { book ->
            bookAdapter.setBooks(book as ArrayList<BookData>)
            hideProgressBar()
        }
    }

    private fun showProgressBar(){
        binding.progressBarBook.visibility = View.VISIBLE
        binding.bookRecycler.visibility = View.INVISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBarBook.visibility = View.GONE
        binding.bookRecycler.visibility = View.VISIBLE
    }


}