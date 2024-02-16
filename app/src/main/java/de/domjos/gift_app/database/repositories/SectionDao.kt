package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.Section
import de.domjos.gift_app.model.SectionSummary

@Dao
interface SectionDao {

    @Query("SELECT * FROM sections")
    fun getAll(): List<Section>

    @Query("SELECT * FROM sections where id=:id")
    fun getById(id: String): Section

    @Insert
    fun insert(sections: Section)

    @Update
    fun update(sections: Section)

    @Delete
    fun delete(sections: Section)


    @Query("SELECT * FROM section_summaries")
    fun getAllSummaries(): List<SectionSummary>

    @Query("SELECT * FROM section_summaries where id=:id")
    fun getSummaryById(id: String): SectionSummary

    @Insert
    fun insertSummary(sections: SectionSummary)

    @Update
    fun updateSummary(sections: SectionSummary)

    @Delete
    fun deleteSummary(sections: SectionSummary)
}