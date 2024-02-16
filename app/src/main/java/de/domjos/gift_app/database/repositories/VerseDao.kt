package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.Verse
import de.domjos.gift_app.model.VerseSummary

@Dao
interface VerseDao {

    @Query("SELECT * FROM verses")
    fun getAll(): List<Verse>

    @Query("SELECT * FROM verses where id=:id")
    fun getById(id: String): Verse

    @Insert
    fun insert(verse: Verse)

    @Update
    fun update(verse: Verse)

    @Delete
    fun delete(verse: Verse)


    @Query("SELECT * FROM verse_summaries")
    fun getAllSummaries(): List<VerseSummary>

    @Query("SELECT * FROM verse_summaries where id=:id")
    fun getSummaryById(id: String): VerseSummary

    @Insert
    fun insertSummary(verse: VerseSummary)

    @Update
    fun updateSummary(verse: VerseSummary)

    @Delete
    fun deleteSummary(verse: VerseSummary)
}