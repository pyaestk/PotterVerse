package com.project.potterverse.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.model.BookDetails
import com.project.potterverse.model.BookDetailsData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailsViewModel(
    private val potterRepository: PotterRepository
): ViewModel() {

    private var bookDetailsLiveData = MutableLiveData<BookDetailsData>()

    fun fetchBookDetails(id: String){
        potterRepository.getBookDetails(id).enqueue(object : Callback<BookDetails>{
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
            potterRepository.insertUpdateBook(book)
        }
    }

    fun deleteBook(book: BookDetailsData) {
        viewModelScope.launch {
            potterRepository.deleteBook(book)
        }
    }

    fun getBookDetailsLiveData(): LiveData<BookDetailsData>{
        return bookDetailsLiveData
    }

    fun getAllFavBook(): LiveData<List<BookDetailsData>> {
        return potterRepository.getFavBooks()
    }
}

