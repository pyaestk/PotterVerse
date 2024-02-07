package com.project.potterverse.room.bookDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.data.bookDetails.BookDetailsData

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateBook(book: BookDetailsData)

    @Delete
    suspend fun deleteBook(book: BookDetailsData)

    @Query("SELECT * FROM bookInformation")
    fun getAllBooks(): LiveData<List<BookDetailsData>>
}