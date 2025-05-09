package com.project.potterverse.data.datasource

import androidx.lifecycle.LiveData
import com.project.potterverse.data.db.AppDatabase
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.CharacterDetailsData


class PotterLocalDatasource(
    val appDatabase: AppDatabase
) {

    fun getFavMovieList(): LiveData<List<MovieDetailData>> {
        return appDatabase.movieDao().getAllMovies()
    }

    fun getFavBookList(): LiveData<List<BookDetailsData>> {
        return appDatabase.bookDao().getAllBooks()
    }

    fun getFavCharacterList(): LiveData<List<CharacterDetailsData>> {
        return appDatabase.characterDao().getAllCharacters()
    }

    suspend fun insertUpdateMovie(movie: MovieDetailData){
        appDatabase.movieDao().insertUpdateMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieDetailData){
        appDatabase.movieDao().deleteMovie(movie)
    }

    suspend fun insertUpdateBook(book: BookDetailsData){
        appDatabase.bookDao().insertUpdateBook(book)
    }

    suspend fun deleteBook(book: BookDetailsData){
        appDatabase.bookDao().deleteBook(book)
    }

    suspend fun insertUpdateCharacter(character: CharacterDetailsData){
        appDatabase.characterDao().insertUpdateChar(character)
    }

    suspend fun deleteCharacter(character: CharacterDetailsData){
        appDatabase.characterDao().deleteChar(character)
    }

}