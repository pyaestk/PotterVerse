package com.project.potterverse.data.repository

import androidx.lifecycle.LiveData
import com.project.potterverse.data.datasource.PotterLocalDatasource
import com.project.potterverse.data.datasource.PotterRemoteDataSource
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movieDetails.MovieDetails
import com.project.potterverse.data.movieDetails.MovieList
import com.project.potterverse.model.BookDetails
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.BooksList
import com.project.potterverse.model.CharacterDetails
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.model.CharactersList
import retrofit2.Call

class PotterRepository(
    private val remoteDataSource: PotterRemoteDataSource,
    private val localDataSource: PotterLocalDatasource
) {

    // Movies
    fun getMovieList(): Call<MovieList> {
        return remoteDataSource.fetchMovieList()
    }
    fun getMovieDetails(id: String): Call<MovieDetails> {
        return remoteDataSource.fetchMovieDetails(id)
    }

    // Books
    fun getBookList(): Call<BooksList> {
        return remoteDataSource.fetchBookList()
    }
    fun getBookDetails(id: String): Call<BookDetails> {
        return remoteDataSource.fetchBookDetails(id)
    }

    // Characters
    fun getCharacterList(pageNumber: Int): Call<CharactersList> {
        return remoteDataSource.fetchCharacterList(pageNumber)
    }
    fun getCharacterDetails(id: String): Call<CharacterDetails> {
        return remoteDataSource.fetchCharacterDetails(id)
    }

    // Search Characters
    fun searchCharacters(nameFilter: String): Call<CharactersList> {
        return remoteDataSource.searchCharacters(nameFilter)
    }

    // get fav items():
    fun getFavMovies(): LiveData<List<MovieDetailData>> {
        return localDataSource.getFavMovieList()
    }
    fun getFavBooks(): LiveData<List<BookDetailsData>> {
        return localDataSource.getFavBookList()
    }
    fun getFavCharacters(): LiveData<List<CharacterDetailsData>> {
        return localDataSource.getFavCharacterList()
    }

    suspend fun insertUpdateMovie(movie: MovieDetailData){
        localDataSource.insertUpdateMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieDetailData){
        localDataSource.deleteMovie(movie)
    }

    suspend fun insertUpdateBook(book: BookDetailsData){
        localDataSource.insertUpdateBook(book)
    }

    suspend fun deleteBook(book: BookDetailsData){
        localDataSource.deleteBook(book)
    }

    suspend fun insertUpdateCharacter(character: CharacterDetailsData){
        localDataSource.insertUpdateCharacter(character)
    }

    suspend fun deleteCharacter(character: CharacterDetailsData){
        localDataSource.deleteCharacter(character)
    }
}