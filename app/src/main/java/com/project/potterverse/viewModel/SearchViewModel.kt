package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.potterverse.Adapter.SearchItem
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.CharacterDetails.CharactersList
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.bookDetails.BooksList
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieList

import com.project.potterverse.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private var movieListLiveData = MutableLiveData<List<MovieDetailData>>()
    private var bookListLiveData = MutableLiveData<List<BookDetailsData>>()
    private var characterListLiveData = MutableLiveData<List<CharacterDetailsData>>()

    fun getMovies(query: String) {
        RetrofitInstance.api.getMovieLists().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                val movieList = response.body()?.data
                movieList?.let {
                    val filteredMovies = if (query.isNotBlank()) {
                        it.filter { movieData ->
                            movieData.attributes.title!!.contains(query, ignoreCase = true)
                        }
                    } else {
                        return
                    }
                    movieListLiveData.postValue(filteredMovies)
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("SearchViewModel", t.toString())
            }
        })
    }

    fun getBooks(query: String) {
        RetrofitInstance.api.getBooksLists().enqueue(object : Callback<BooksList> {
            override fun onResponse(call: Call<BooksList>, response: Response<BooksList>) {
                val bookList = response.body()?.data
                bookList?.let {
                    val filteredBooks = if (query.isNotBlank()) {
                        it.filter { bookData ->
                            bookData.attributes.title.contains(query, ignoreCase = true)
                        }
                    } else {
                        return
                    }
                    bookListLiveData.postValue(filteredBooks)
                }
            }

            override fun onFailure(call: Call<BooksList>, t: Throwable) {
                Log.e("SearchViewModel", t.toString())
            }
        })
    }

    fun searchCharacters(query: String) {
        RetrofitInstance.api.getCharactersResults(query).enqueue(object : Callback<CharactersList>{
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
                Log.e("SearchViewModel", t.toString())
            }

        })

    }

    fun observeItemListLiveData(): LiveData<List<SearchItem>> {
        val combinedLiveData = MediatorLiveData<List<SearchItem>>()

        combinedLiveData.addSource(movieListLiveData) { movieList ->
            val combinedList = ArrayList<SearchItem>().apply {
                addAll(movieList?.map { SearchItem.Movie(it) } ?: emptyList())
                addAll(bookListLiveData.value?.map { SearchItem.Book(it) } ?: emptyList())
                addAll(characterListLiveData.value?.map { SearchItem.Character(it) } ?: emptyList())
            }
            combinedLiveData.value = combinedList
        }

        combinedLiveData.addSource(bookListLiveData) { bookList ->
            val combinedList = ArrayList<SearchItem>().apply {
                addAll(bookList?.map { SearchItem.Book(it) } ?: emptyList())
                addAll(movieListLiveData.value?.map { SearchItem.Movie(it) } ?: emptyList())
                addAll(characterListLiveData.value?.map { SearchItem.Character(it) } ?: emptyList())
            }
            combinedLiveData.value = combinedList
        }

        combinedLiveData.addSource(characterListLiveData) { characterList ->
            val combinedList = ArrayList<SearchItem>().apply {
                addAll(characterList?.map { SearchItem.Character(it) } ?: emptyList())
                addAll(movieListLiveData.value?.map { SearchItem.Movie(it) } ?: emptyList())
                addAll(bookListLiveData.value?.map { SearchItem.Book(it) } ?: emptyList())
            }
            combinedLiveData.value = combinedList
        }

        return combinedLiveData
    }
}
