package com.project.potterverse.data.retrofit

import com.project.potterverse.data.BooksList
import com.project.potterverse.data.movies.MovieList
import retrofit2.Call
import retrofit2.http.GET

interface PotterApi {

    @GET("movies/")
    fun getMovieLists():Call<MovieList>

    @GET("books/")
    fun getBooksLists():Call<BooksList>

}