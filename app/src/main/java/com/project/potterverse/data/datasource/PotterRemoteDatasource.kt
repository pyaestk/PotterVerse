package com.project.potterverse.data.datasource


import com.project.potterverse.model.BookDetails
import com.project.potterverse.model.BooksList
import com.project.potterverse.model.CharacterDetails
import com.project.potterverse.model.CharactersList
import com.project.potterverse.data.movieDetails.MovieDetails
import com.project.potterverse.data.movieDetails.MovieList
import com.project.potterverse.data.service.PotterApi
import retrofit2.Call

class PotterRemoteDataSource(private val api: PotterApi) {

    // Movies
    fun fetchMovieList(): Call<MovieList> {
        return api.getMovieLists()
    }
    fun fetchMovieDetails(id: String): Call<MovieDetails> {
        return api.getMovieDetails(id)
    }

    // Books
    fun fetchBookList(): Call<BooksList> {
        return api.getBooksLists()
    }
    fun fetchBookDetails(id: String): Call<BookDetails> {
        return api.getBookDetails(id)
    }

    // Characters
    fun fetchCharacterList(pageNumber: Int): Call<CharactersList> {
        return api.getCharacterLists(pageNumber)
    }
    fun fetchCharacterDetails(id: String): Call<CharacterDetails> {
        return api.getCharacterDetails(id)
    }
    // Search Characters
    fun searchCharacters(nameFilter: String): Call<CharactersList> {
        return api.getCharactersResults(nameFilter)
    }
}
