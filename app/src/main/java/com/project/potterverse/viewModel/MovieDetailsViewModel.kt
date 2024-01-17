package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.potterverse.data.MovieDetailData
import com.project.potterverse.data.MovieDetails
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.data.movies.MovieList
import com.project.potterverse.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieDetailsViewModel: ViewModel() {
    private var movieDetailLiveData = MutableLiveData<MovieDetailData>()

    fun fetchMovieDetails(id: String) {
        RetrofitInstance.api.getMovieDetails(id).enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                response.body()?.let {
                    movieDetailLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.e("ErrorFetchDetail", t.toString())
            }
        })
    }

    fun getMovieDetailsLiveData(): LiveData<MovieDetailData> {
        return movieDetailLiveData
    }
}