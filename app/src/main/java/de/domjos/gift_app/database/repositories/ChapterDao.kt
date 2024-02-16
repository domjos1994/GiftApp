package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.Chapter
import de.domjos.gift_app.model.ChapterSummary

@Dao
interface ChapterDao {

    @Query("SELECT * FROM chapters")
    fun getAll(): List<Chapter>

    @Query("SELECT * FROM chapters where id=:id")
    fun getById(id: String): Chapter

    @Insert
    fun insert(chapter: Chapter)

    @Update
    fun update(chapter: Chapter)

    @Delete
    fun delete(chapter: Chapter)


    @Query("SELECT * FROM chapter_summaries")
    fun getAllSummaries(): List<ChapterSummary>

    @Query("SELECT * FROM chapter_summaries where id=:id")
    fun getSummaryById(id: String): ChapterSummary

    @Insert
    fun insertSummary(chapter: ChapterSummary)

    @Update
    fun updateSummary(chapter: ChapterSummary)

    @Delete
    fun deleteSummary(chapter: ChapterSummary)
}