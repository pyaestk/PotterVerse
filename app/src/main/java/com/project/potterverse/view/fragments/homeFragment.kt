package com.project.potterverse.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.FragmentHomeBinding
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.Adapter.BaseBookAdapter
import com.project.potterverse.view.Adapter.BaseCharacterAdapter
import com.project.potterverse.view.Adapter.BaseMovieAdapter
import com.project.potterverse.view.activities.BookDetailsActivity
import com.project.potterverse.view.activities.CharacterDetailsActivity
import com.project.potterverse.view.activities.MovieDetailsActivity
import com.project.potterverse.view.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class homeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var movieAdapter: BaseMovieAdapter
    private lateinit var bookAdapter: BaseBookAdapter
    private lateinit var characterAdapter: BaseCharacterAdapter
    private var pageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = (activity as MainActivity).viewModel
        movieAdapter = BaseMovieAdapter(2)
        bookAdapter = BaseBookAdapter(2)
        characterAdapter = BaseCharacterAdapter(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        //for movies
        forMovies()
        //for books
        forBooks()
        //for characters
        forCharacters()
    }

    private fun forCharacters() {
        var isLoading = false

        val layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
        binding.characterRecycler.layoutManager = layoutManager

        binding.characterRecycler.adapter = characterAdapter

        binding.characterRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val isLastItemVisible = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0

                if (!isLoading && isLastItemVisible) {
                    // End of the list reached, load more data
                    isLoading = true
                    pageNumber++
                    viewModel.fetchCharacters(pageNumber)
                }
            }
        })

        viewModel.fetchCharacters(pageNumber)
        viewModel.characterList.observe(viewLifecycleOwner) { character ->
            characterAdapter.addCharacters(character as ArrayList<CharacterDetailsData>)
            hideProgressBar()
            isLoading = false
        }

        characterAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(Constant.chrId, chr.id)
            intent.putExtra(Constant.chrName, chr.attributes.name)
            intent.putExtra(Constant.chrImage, chr.attributes.image)
            intent.putExtra(Constant.chrSpecies, chr.attributes.species)
            intent.putExtra(Constant.chrGender, chr.attributes.gender)
            startActivity(intent)
        }
    }

    private fun forBooks() {
        binding.booksRecycler.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }
        viewModel.fetchBooks()
        viewModel.bookList.observe(viewLifecycleOwner) {book ->
            bookAdapter.setBooks(book as ArrayList<BookDetailsData>)
            hideProgressBar()
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
    }


    private fun forMovies() {
        binding.MovieRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }
        viewModel.fetchMovies()
        viewModel.movieList.observe(viewLifecycleOwner) {movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieDetailData>)
        }
        movieAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(Constant.movieID, movie.id)
            intent.putExtra(Constant.movieTitle, movie.attributes.title)
            intent.putExtra(Constant.movieImage, movie.attributes.poster)
            intent.putExtra(Constant.movieDate, movie.attributes.release_date)
            intent.putExtra(Constant.movieRating, movie.attributes.rating)
            intent.putExtra(Constant.movieBo, movie.attributes.box_office)
            intent.putExtra(Constant.movieDirector, movie.attributes.directors?.get(0))
            startActivity(intent)
        }

    }

    private fun showProgressBar(){
        requireActivity().runOnUiThread {
            binding.homeFragmentLayout.visibility = View.INVISIBLE
        }
    }
    private fun hideProgressBar(){
        requireActivity().runOnUiThread {
            binding.progressBar2.visibility = View.GONE
            binding.homeFragmentLayout.visibility = View.VISIBLE
        }
    }

}