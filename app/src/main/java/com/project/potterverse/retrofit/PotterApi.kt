package com.project.potterverse.retrofit

import com.project.potterverse.data.BooksList
import com.project.potterverse.data.CharacterDetails.CharacterDetails
import com.project.potterverse.data.CharactersList
import com.project.potterverse.data.movieDetails.MovieDetails
import com.project.potterverse.data.bookDetails.BookDetails
import com.project.potterverse.data.movies.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PotterApi {

    @GET("movies")
    fun getMovieLists():Call<MovieList>

    @GET("movies/{id}")
    fun getMovieDetails(@Path("id") id: String): Call<MovieDetails>

    @GET("books")
    fun getBooksLists():Call<BooksList>

    @GET("books/{id}")
    fun getBookDetails(@Path("id") id: String): Call<BookDetails>

    @GET("characters?")
    fun getCharacterLists(
        @Query("page[number]") pageNumber: Int
    ):Call<CharactersList>

    @GET("characters/{id}")
    fun getCharacterDetails(
        @Path("id") id: String
    ): Call<CharacterDetails>
}