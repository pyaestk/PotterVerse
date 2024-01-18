package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.potterverse.data.bookDetails.BookDetails
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailsViewModel: ViewModel() {

    private var bookDetailsLiveData = MutableLiveData<BookDetailsData>()

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

    fun getBookDetailsLiveData(): LiveData<BookDetailsData>{
        return bookDetailsLiveData
    }
}