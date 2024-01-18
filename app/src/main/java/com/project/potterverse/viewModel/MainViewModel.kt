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
import com.project.potterverse.data.BookData
import com.project.potterverse.data.BooksList
import com.project.potterverse.data.CharactersData
import com.project.potterverse.data.CharactersList
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.data.movies.MovieList
import com.project.potterverse.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private var movieListLiveData = MutableLiveData<List<MovieData>>()
    private var bookListLiveData = MutableLiveData<List<BookData>>()
    private var characterListLiveData = MutableLiveData<List<CharactersData>>()

    
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
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
                val errorMessage = "Failed to fetch movie list."
                _toastMessage.postValue(errorMessage)
            }
        })
    }
    
    fun getMovieListLiveData(): LiveData<List<MovieData>> {
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
                val errorMessage = "Failed to fetch Book list."
                _toastMessage.postValue(errorMessage)
            }

        })
    }
    fun getBookListLiveData(): LiveData<List<BookData>> {
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
                val errorMessage = "Failed to fetch character list."
                _toastMessage.postValue(errorMessage)
            }
        })
    }
    fun getCharacterListLiveData(): LiveData<List<CharactersData>> {
        return characterListLiveData
    }
    

}

class MainViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}
