package com.project.potterverse.view.fragments.subFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.project.potterverse.view.Adapter.BaseBookAdapter
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.databinding.FragmentBooksBinding
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.viewModel.MainViewModel
import com.project.potterverse.view.MainActivity
import com.project.potterverse.view.activities.BookDetailsActivity

class BooksFragment : Fragment() {

    lateinit var binding: FragmentBooksBinding
    lateinit var bookAdapter: BaseBookAdapter
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        bookAdapter = BaseBookAdapter(1)
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
        bookAdapter.onItemClick = { book ->
            val intent = Intent(activity, BookDetailsActivity::class.java)
            intent.putExtra(Constant.bookID, book.id)
            intent.putExtra(Constant.bookAuthor, book.attributes.author)
            intent.putExtra(Constant.bookImage, book.attributes.cover)
            intent.putExtra(Constant.bookDate, book.attributes.release_date)
            intent.putExtra(Constant.bookTitle, book.attributes.title)
            intent.putExtra(Constant.bookChapter, book.relationships.chapters.data.size.toString())
            startActivity(intent)
        }


        viewModel.fetchBooks()
        viewModel.bookList.observe(viewLifecycleOwner) { book ->
            bookAdapter.setBooks(book as ArrayList<BookDetailsData>)
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