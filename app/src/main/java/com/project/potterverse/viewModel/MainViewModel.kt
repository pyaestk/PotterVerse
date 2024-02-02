package com.project.potterverse.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.CharacterDetails.CharactersList
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.bookDetails.BooksList
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieList
import com.project.potterverse.retrofit.RetrofitInstance
import com.project.potterverse.room.MovieDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val movieDatabase: MovieDatabase): ViewModel() {

    private var movieListLiveData = MutableLiveData<List<MovieDetailData>>()
    private var bookListLiveData = MutableLiveData<List<BookDetailsData>>()
    private var characterListLiveData = MutableLiveData<List<CharacterDetailsData>>()

    private var favMovieLiveData = movieDatabase.movieDao().getAllMovies()

    //for movies list home screen
    fun getMovies() {
        RetrofitInstance.api.getMovieLists().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                val movieList = response.body()?.data
                movieList?.let {
                    movieListLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("ErrorFetch", t.toString())
            }
        })
    }
    
    fun getMovieListLiveData(): LiveData<List<MovieDetailData>> {
        return movieListLiveData
    }

    //for books list home screen
    fun getBooks() {
        RetrofitInstance.api.getBooksLists().enqueue(object : Callback<BooksList>{
            override fun onResponse(call: Call<BooksList>, response: Response<BooksList>) {
                val bookList = response.body()?.data
                bookList?.let {
                    bookListLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<BooksList>, t: Throwable) {
                Log.e("ErrorFetch", t.toString())
            }

        })
    }
    fun getBookListLiveData(): LiveData<List<BookDetailsData>> {
        return bookListLiveData
    }

    //for characters list
    fun getCharacters(pageNumber: Int) {
        RetrofitInstance.api.getCharacterLists(pageNumber).enqueue(object : Callback<CharactersList> {
            override fun onResponse(
                call: Call<CharactersList>,
                response: Response<CharactersList>
            ) {
                val characterList = response.body()?.data
                characterList?.let {
                    characterListLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<CharactersList>, t: Throwable) {
                Log.e("ErrorFetch", t.toString())
            }
        })
    }
    fun getCharacterListLiveData(): LiveData<List<CharacterDetailsData>> {
        return characterListLiveData
    }

    //for fav-movie
    fun observerFavMovieLiveData(): LiveData<List<MovieDetailData>>{
        return favMovieLiveData
    }


}

class MainViewModelFactory(private val movieDatabase: MovieDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(movieDatabase) as T
    }
}
