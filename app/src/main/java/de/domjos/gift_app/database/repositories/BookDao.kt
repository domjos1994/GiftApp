package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books where id=:id")
    fun getById(id: String): Book

    @Insert
    fun insert(book: Book)

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}