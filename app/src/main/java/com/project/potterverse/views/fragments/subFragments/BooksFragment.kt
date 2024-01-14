package com.project.potterverse.views.fragments.subFragments

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
        }

        viewModel.getBooks()
        viewModel.getBookListLiveData().observe(viewLifecycleOwner) { book ->
            bookAdapter.setBooks(book as ArrayList<BookData>)
            hideProgressBar()
        }
    }

    private fun showProgressBar(){
        binding.progressBarBook.visibility = View.VISIBLE
        binding.bookFragmentLayout.visibility = View.INVISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBarBook.visibility = View.GONE
        binding.bookFragmentLayout.visibility = View.VISIBLE
    }


}