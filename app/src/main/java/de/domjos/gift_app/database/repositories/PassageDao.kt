package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.Passage

@Dao
interface PassageDao {
    @Query("SELECT * FROM passages")
    fun getAll(): List<Passage>

    @Query("SELECT * FROM passages where id=:id")
    fun getById(id: String): Passage

    @Insert
    fun insert(passage: Passage)

    @Update
    fun update(passage: Passage)

    @Delete
    fun delete(passage: Passage)
}