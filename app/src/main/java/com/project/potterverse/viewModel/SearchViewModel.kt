package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class SearchViewModel : ViewModel() {

    private var movieListLiveData = MutableLiveData<List<MovieData>>()
    private var bookListLiveData = MutableLiveData<List<BookData>>()
//    private var characterListLiveData = MutableLiveData<List<CharactersData>>()

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

    fun getBooks() {
        RetrofitInstance.api.getBooksLists().enqueue(object : Callback<BooksList> {
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

    fun observeItemListLiveData(): LiveData<List<Any>> {
        val combinedLiveData = MediatorLiveData<List<Any>>()


        combinedLiveData.addSource(movieListLiveData) { movieList ->
            val combinedList = ArrayList<Any>()
            combinedList.addAll(movieList)
            bookListLiveData.value?.let { combinedList.addAll(it) }
            combinedLiveData.value = combinedList

        }

        combinedLiveData.addSource(bookListLiveData) { bookList ->
            val combinedList = ArrayList<Any>()
            combinedList.addAll(bookList)
            movieListLiveData.value?.let { combinedList.addAll(it) }
            combinedLiveData.value = combinedList

        }



        return combinedLiveData
    }
}
