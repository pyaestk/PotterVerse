package com.project.potterverse.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.bookmark.FavBookAdapter
import com.project.potterverse.Adapter.bookmark.FavCharacterAdapter
import com.project.potterverse.Adapter.bookmark.FavMovieAdapter
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.FragmentFavoritesBinding
import com.project.potterverse.utils.Constant
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.BookDetailsActivity
import com.project.potterverse.views.activities.CharacterDetailsActivity
import com.project.potterverse.views.activities.MovieDetailsActivity


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: FavMovieAdapter
    private lateinit var bookAdapter: FavBookAdapter
    private lateinit var charAdapter: FavCharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = FavMovieAdapter()
        bookAdapter = FavBookAdapter()
        charAdapter = FavCharacterAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.movieFavRecycler.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = movieAdapter

            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        binding.bookFavRecycler.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = bookAdapter

            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = bookAdapter
        }

        binding.charFavRecycler.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = charAdapter
        }

        //for movie
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
        viewModel.observerFavMovieLiveData().observe(requireActivity()){ movie ->
            movieAdapter.setFavMovies(movie as ArrayList<MovieDetailData>)

            if (movieAdapter.itemCount < 1){
                binding.seeAllFavMovie.visibility = View.GONE
                binding.movieFavRecycler.visibility = View.GONE
            } else {
                binding.seeAllFavMovie.visibility = View.VISIBLE
                binding.movieFavRecycler.visibility = View.VISIBLE
            }
        }

        viewModel.observerFavBookLiveData().observe(requireActivity()){book ->
            bookAdapter.setFavBooks(book as ArrayList<BookDetailsData>)
            if (bookAdapter.itemCount < 1){
                binding.seeAllFavBook.visibility = View.GONE
                binding.bookFavRecycler.visibility = View.GONE
            } else {
                binding.seeAllFavBook.visibility = View.VISIBLE
                binding.bookFavRecycler.visibility = View.VISIBLE
            }
        }

        viewModel.observerFavCharLiveData().observe(requireActivity()) { char ->
            charAdapter.setFavChars(char as ArrayList<CharacterDetailsData>)
            if (charAdapter.itemCount < 1){
                binding.seeAllFavChar.visibility = View.GONE
                binding.charFavRecycler.visibility = View.GONE
            } else {
                binding.seeAllFavChar.visibility = View.VISIBLE
                binding.charFavRecycler.visibility = View.VISIBLE
            }
        }

    }

}