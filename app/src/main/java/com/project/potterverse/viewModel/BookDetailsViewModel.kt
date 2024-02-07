package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.bookDetails.BookDetails
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.retrofit.RetrofitInstance
import com.project.potterverse.room.bookDb.BookDatabase
import com.project.potterverse.room.movieDb.MovieDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailsViewModel(private val bookDatabase: BookDatabase): ViewModel() {

    private var bookDetailsLiveData = MutableLiveData<BookDetailsData>()
    private var favBookLiveData = bookDatabase.bookDao().getAllBooks()

    fun fetchBookDetails(id: String){
        RetrofitInstance.api.getBookDetails(id).enqueue(object : Callback<BookDetails>{
            override fun onResponse(call: Call<BookDetails>, response: Response<BookDetails>) {
                response.body()?.let {
                    bookDetailsLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<BookDetails>, t: Throwable) {
                Log.e("Error Fetching on Book Details", t.message.toString())
            }

        })
    }

    fun insertBook(book: BookDetailsData) {
        viewModelScope.launch {
            bookDatabase.bookDao().insertUpdateBook(book)
        }
    }

    fun deleteBook(book: BookDetailsData) {
        viewModelScope.launch {
            bookDatabase.bookDao().deleteBook(book)
        }
    }

    fun getAllBooks(): LiveData<List<BookDetailsData>> {
        return favBookLiveData
    }

    fun getBookDetailsLiveData(): LiveData<BookDetailsData>{
        return bookDetailsLiveData
    }
}

class BookDetailViewModelFactory(private val bookDatabase: BookDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookDetailsViewModel(bookDatabase) as T
    }
}