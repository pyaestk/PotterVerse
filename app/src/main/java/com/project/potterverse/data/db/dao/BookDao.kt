package com.project.potterverse.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.model.BookDetailsData

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateBook(book: BookDetailsData)

    @Delete
    suspend fun deleteBook(book: BookDetailsData)

    @Query("SELECT * FROM bookInformation")
    fun getAllBooks(): LiveData<List<BookDetailsData>>
}