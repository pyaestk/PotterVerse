package com.project.potterverse.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.Adapter.BaseBookAdapter
import com.project.potterverse.Adapter.BaseCharacterAdapter
import com.project.potterverse.Adapter.BaseMovieAdapter
import com.project.potterverse.R
import com.project.potterverse.data.BookData
import com.project.potterverse.data.CharactersData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentHomeBinding
import com.project.potterverse.viewModel.CharacterDetailsViewModel
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.BookDetailsActivity
import com.project.potterverse.views.activities.CharacterDetailsActivity
import com.project.potterverse.views.activities.MovieDetailsActivity

class homeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel
    lateinit var movieAdapter: BaseMovieAdapter
    lateinit var bookAdapter: BaseBookAdapter
    lateinit var characterAdapter: BaseCharacterAdapter
    private var pageNumber = 1

    companion object{
        const val movieID = "MOVIE_ID"
        const val movieDate = "MOVIE_DATE"
        const val movieTitle = "MOVIE_TITLE"
        const val movieImage = "MOVIE_POSTER"
        const val movieDirector = "MOVIE_DIR"
        const val movieRating = "MOVIE_RATING"
        const val movieBo = "MOVIE_BOX_OFFICE"

        const val bookID = "BOOK_ID"
        const val bookTitle = "BOOK_TITLE"
        const val bookDate = "BOOK_DATE"
        const val bookAuthor = "BOOK_AUTHOR"
        const val bookChapter = "BOOK_CHAPTER"
        const val bookImage = "BOOK_IMAGE"

        const val chrId = "CHARACTER_ID"
        const val chrImage = "CHARACTER_IMAGE"
        const val chrName = "CHARACTER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
                    viewModel.getCharacters(pageNumber)
                }
            }
        })

        viewModel.getCharacters(pageNumber)
        viewModel.getCharacterListLiveData().observe(viewLifecycleOwner) { character ->
            characterAdapter.addCharacters(character as ArrayList<CharactersData>)
            hideProgressBar()
            isLoading = false
        }

        characterAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(chrId, chr.id)
            intent.putExtra(chrName, chr.attributes.name)
            intent.putExtra(chrImage, chr.attributes.image)
            startActivity(intent)
        }
    }

    private fun forBooks() {
        binding.booksRecycler.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }
        viewModel.getBooks()
        viewModel.getBookListLiveData().observe(viewLifecycleOwner) {book ->
            bookAdapter.setBooks(book as ArrayList<BookData>)
            hideProgressBar()
        }
        bookAdapter.onItemClick = { book ->
            val intent = Intent(activity, BookDetailsActivity::class.java)
            intent.putExtra(bookID, book.id)
            intent.putExtra(bookAuthor, book.attributes.author)
            intent.putExtra(bookImage, book.attributes.cover)
            intent.putExtra(bookDate, book.attributes.release_date)
            intent.putExtra(bookTitle, book.attributes.title)
            intent.putExtra(bookChapter, book.relationships.chapters.data.size.toString())
            startActivity(intent)
        }
    }


    private fun forMovies() {
        binding.MovieRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }
        viewModel.getMovies()
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) {movie ->
            movieAdapter.setMovies(movie as ArrayList<MovieData>)
            hideProgressBar()
        }
        movieAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(movieID, movie.id)
            intent.putExtra(movieTitle, movie.attributes.title)
            intent.putExtra(movieImage, movie.attributes.poster)
            intent.putExtra(movieDate, movie.attributes.release_date)
            intent.putExtra(movieRating, movie.attributes.rating)
            intent.putExtra(movieBo, movie.attributes.box_office)
            intent.putExtra(movieDirector, movie.attributes.directors[0])
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