package com.project.potterverse.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieList
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.BooksList
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.model.CharactersList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
    private val potterRepository: PotterRepository
) : ViewModel() {

    private var movieListLiveData = MutableLiveData<List<MovieDetailData>>()
    private var bookListLiveData = MutableLiveData<List<BookDetailsData>>()
    private var characterListLiveData = MutableLiveData<List<CharacterDetailsData>>()

    private val selectedFilter = MutableLiveData<FilterType>(FilterType.ALL)

    fun getMovies(query: String) {
        potterRepository.getMovieList().enqueue(object : Callback<MovieList> {
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
        potterRepository.getBookList().enqueue(object : Callback<BooksList> {
            override fun onResponse(call: Call<BooksList>, response: Response<BooksList>) {
                val bookList = response.body()?.data
                bookList?.let {
                    val filteredBooks = if (query.isNotBlank()) {
                        it.filter { bookData ->
                            bookData.attributes.title!!.contains(query, ignoreCase = true)
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
        potterRepository.searchCharacters(query).enqueue(object : Callback<CharactersList>{
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

    private var searchJob: Job? = null

    fun searchAll(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // debounce delay
            if (query.isBlank()) {
                // Clear all results
                movieListLiveData.postValue(emptyList())
                bookListLiveData.postValue(emptyList())
                characterListLiveData.postValue(emptyList())
            } else {
                searchCharacters(query)
                getMovies(query)
                getBooks(query)
            }
        }
    }

    fun setFilter(filter: FilterType) {
        selectedFilter.value = filter
    }

    fun observeItemListLiveData(): LiveData<List<SearchItem>> {
        val combinedLiveData = MediatorLiveData<List<SearchItem>>()

        val updateCombinedList = {
            val movieList = movieListLiveData.value ?: emptyList()
            val bookList = bookListLiveData.value ?: emptyList()
            val characterList = characterListLiveData.value ?: emptyList()
            val filter = selectedFilter.value ?: FilterType.ALL

            val combinedList = ArrayList<SearchItem>().apply {
                when (filter) {
                    FilterType.ALL -> {
                        addAll(movieList.map { SearchItem.Movie(it) })
                        addAll(bookList.map { SearchItem.Book(it) })
                        addAll(characterList.map { SearchItem.Character(it) })
                    }
                    FilterType.MOVIES -> addAll(movieList.map { SearchItem.Movie(it) })
                    FilterType.BOOKS -> addAll(bookList.map { SearchItem.Book(it) })
                    FilterType.CHARACTERS -> addAll(characterList.map { SearchItem.Character(it) })
                }
            }

            combinedLiveData.value = combinedList
        }

        combinedLiveData.addSource(movieListLiveData) { updateCombinedList() }
        combinedLiveData.addSource(bookListLiveData) { updateCombinedList() }
        combinedLiveData.addSource(characterListLiveData) { updateCombinedList() }
        combinedLiveData.addSource(selectedFilter) { updateCombinedList() }

        return combinedLiveData
    }

}

sealed class SearchItem {
    data class Book(val data: BookDetailsData) : SearchItem()
    data class Movie(val data: MovieDetailData) : SearchItem()
    data class Character(val data: CharacterDetailsData) : SearchItem()
}
enum class FilterType {
    ALL, MOVIES, BOOKS, CHARACTERS
}