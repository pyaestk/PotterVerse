package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieDetails
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.data.movies.MovieList
import com.project.potterverse.retrofit.RetrofitInstance
import com.project.potterverse.room.MovieDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieDetailsViewModel(private val movieDatabase: MovieDatabase): ViewModel() {
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

    fun insertMovie(movie: MovieDetailData) {
        viewModelScope.launch {
            movieDatabase.movieDao().insertUpdateMovie(movie)
        }
    }

    fun deleteMovies(movie: MovieDetailData) {
        viewModelScope.launch {
            movieDatabase.movieDao().deleteMovie(movie)
        }
    }
}
class MovieDetailViewModelFactory(private val movieDatabase: MovieDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieDatabase) as T
    }
}