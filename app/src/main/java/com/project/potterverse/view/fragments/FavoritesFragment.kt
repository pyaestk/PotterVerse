package com.project.potterverse.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.FragmentFavoritesBinding
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.Adapter.bookmark.FavBookAdapter
import com.project.potterverse.view.Adapter.bookmark.FavCharacterAdapter
import com.project.potterverse.view.Adapter.bookmark.FavMovieAdapter
import com.project.potterverse.view.activities.BookDetailsActivity
import com.project.potterverse.view.activities.CharacterDetailsActivity
import com.project.potterverse.view.activities.MovieDetailsActivity
import com.project.potterverse.view.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var movieAdapter: FavMovieAdapter
    private lateinit var bookAdapter: FavBookAdapter
    private lateinit var charAdapter: FavCharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = (activity as MainActivity).viewModel
        movieAdapter = FavMovieAdapter()
        bookAdapter = FavBookAdapter()
        charAdapter = FavCharacterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.movieFavRecycler.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        binding.bookFavRecycler.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = bookAdapter
        }

        binding.charFavRecycler.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = charAdapter
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    MaterialAlertDialogBuilder(
                        requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog
                    ).setTitle("Remove all bookmarks!")
                        .setMessage("Are you sure you want to remove all your bookmarked items?")
                        .setNegativeButton("No") { dialog, which ->
                            // Respond to negative button press
                        }.setPositiveButton("Yes") { dialog, which ->
                            // Respond to positive button press
                            viewModel.removedAllBookMarks()
                        }.show()
                    true
                }

                else -> false
            }
        }

        observeFavitem()
        onFavMovieClick()
        onFavBookClick()
        onFavCharClick()
    }


    //onItemClick
    private fun onFavBookClick() {
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

    private fun onFavMovieClick() {
        movieAdapter.onItemClick = { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
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

    private fun onFavCharClick() {
        charAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(Constant.chrId, chr.id)
            intent.putExtra(Constant.chrName, chr.attributes.name)
            intent.putExtra(Constant.chrImage, chr.attributes.image)
            intent.putExtra(Constant.chrSpecies, chr.attributes.species)
            intent.putExtra(Constant.chrGender, chr.attributes.gender)
            startActivity(intent)
        }
    }

    //observer
    private fun observeFavitem() {
        viewModel.favoriteMovies.observe(requireActivity()) { movie ->
            movieAdapter.setFavMovies(movie as ArrayList<MovieDetailData>)
            binding.seeAllFavMovie.visibility =
                if (movieAdapter.itemCount > 0) View.VISIBLE else View.GONE
            binding.movieFavRecycler.visibility = binding.seeAllFavMovie.visibility

            checkIfAllFavoritesEmpty()
        }

        viewModel.favoriteBooks.observe(requireActivity()) { book ->
            bookAdapter.setFavBooks(book as ArrayList<BookDetailsData>)
            binding.seeAllFavBook.visibility =
                if (bookAdapter.itemCount > 0) View.VISIBLE else View.GONE
            binding.bookFavRecycler.visibility = binding.seeAllFavBook.visibility

            checkIfAllFavoritesEmpty()
        }

        viewModel.favoriteCharacters.observe(requireActivity()) { char ->
            charAdapter.setFavChars(char as ArrayList<CharacterDetailsData>)
            binding.seeAllFavChar.visibility =
                if (charAdapter.itemCount > 0) View.VISIBLE else View.GONE
            binding.charFavRecycler.visibility = binding.seeAllFavChar.visibility

            checkIfAllFavoritesEmpty()
        }
    }

    private fun checkIfAllFavoritesEmpty() {
        val allEmpty =
            movieAdapter.itemCount == 0 && bookAdapter.itemCount == 0 && charAdapter.itemCount == 0

        binding.tvNoBookmarks.visibility = if (allEmpty) View.VISIBLE else View.GONE
        binding.ivNoBookmarks.visibility = if (allEmpty) View.VISIBLE else View.GONE
    }


}