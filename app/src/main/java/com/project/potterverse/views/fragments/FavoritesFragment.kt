package com.project.potterverse.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.Adapter.BaseCharacterAdapter
import com.project.potterverse.Adapter.FavBookAdapter
import com.project.potterverse.Adapter.FavMovieAdapter
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.FragmentFavoritesBinding
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
    private lateinit var charAdapter: BaseCharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        movieAdapter = FavMovieAdapter()
        bookAdapter = FavBookAdapter()
        charAdapter = BaseCharacterAdapter(false)
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
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }

        binding.bookFavRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }

        binding.charFavRecycler.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
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
            intent.putExtra(homeFragment.bookID, book.id)
            intent.putExtra(homeFragment.bookAuthor, book.attributes.author)
            intent.putExtra(homeFragment.bookImage, book.attributes.cover)
            intent.putExtra(homeFragment.bookDate, book.attributes.release_date)
            intent.putExtra(homeFragment.bookTitle, book.attributes.title)
            intent.putExtra(homeFragment.bookChapter, book.relationships.chapters.data.size.toString())
            startActivity(intent)
        }
    }
    private fun onFavMovieClick() {
        movieAdapter.onItemClick = { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra(homeFragment.movieID, movie.id)
            intent.putExtra(homeFragment.movieTitle, movie.attributes.title)
            intent.putExtra(homeFragment.movieImage, movie.attributes.poster)
            intent.putExtra(homeFragment.movieDate, movie.attributes.release_date)
            intent.putExtra(homeFragment.movieRating, movie.attributes.rating)
            intent.putExtra(homeFragment.movieBo, movie.attributes.box_office)
            intent.putExtra(homeFragment.movieDirector, movie.attributes.directors?.get(0))
            startActivity(intent)
        }
    }
    private fun onFavCharClick() {
        charAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(homeFragment.chrId, chr.id)
            intent.putExtra(homeFragment.chrName, chr.attributes.name)
            intent.putExtra(homeFragment.chrImage, chr.attributes.image)
            intent.putExtra(homeFragment.chrSpecies, chr.attributes.species)
            intent.putExtra(homeFragment.chrGender, chr.attributes.gender)
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
            charAdapter.addCharacters(char as ArrayList<CharacterDetailsData>)
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