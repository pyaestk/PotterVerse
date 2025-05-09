package com.project.potterverse.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.model.CharactersList
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.BooksList
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieList
import com.project.potterverse.data.db.AppDatabase
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.data.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val repository: PotterRepository
) : ViewModel() {

    // Remote data LiveData
    private val _movieList = MutableLiveData<List<MovieDetailData>>()
    val movieList: LiveData<List<MovieDetailData>> = _movieList

    private val _bookList = MutableLiveData<List<BookDetailsData>>()
    val bookList: LiveData<List<BookDetailsData>> = _bookList

    private val _characterList = MutableLiveData<List<CharacterDetailsData>>()
    val characterList: LiveData<List<CharacterDetailsData>> = _characterList

    // Local database LiveData (Favorites)
    val favoriteMovies: LiveData<List<MovieDetailData>> = repository.getFavMovies()
    val favoriteBooks: LiveData<List<BookDetailsData>> = repository.getFavBooks()
    val favoriteCharacters: LiveData<List<CharacterDetailsData>> = repository.getFavCharacters()

    // Fetch remote movies
    fun fetchMovies() {
        repository.getMovieList().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                response.body()?.data?.let {
                    _movieList.postValue(it)
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("MainViewModel", "Error fetching movies: ${t.message}")
            }
        })
    }

    // Fetch remote books
    fun fetchBooks() {
        repository.getBookList().enqueue(object : Callback<BooksList> {
            override fun onResponse(call: Call<BooksList>, response: Response<BooksList>) {
                response.body()?.data?.let {
                    _bookList.postValue(it)
                }
            }

            override fun onFailure(call: Call<BooksList>, t: Throwable) {
                Log.e("MainViewModel", "Error fetching books: ${t.message}")
            }
        })
    }

    // Fetch remote characters
    fun fetchCharacters(pageNumber: Int) {
        repository.getCharacterList(pageNumber).enqueue(object : Callback<CharactersList> {
            override fun onResponse(call: Call<CharactersList>, response: Response<CharactersList>) {
                response.body()?.data?.let {
                    _characterList.postValue(it)
                }
            }

            override fun onFailure(call: Call<CharactersList>, t: Throwable) {
                Log.e("MainViewModel", "Error fetching characters: ${t.message}")
            }
        })
    }
}

class MainViewModelFactory(
    private val repository: PotterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(repository) as T
    }
}
