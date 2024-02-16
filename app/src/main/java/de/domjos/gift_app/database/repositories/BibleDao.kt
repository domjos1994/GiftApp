package de.domjos.gift_app.database.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.model.Country
import de.domjos.gift_app.model.Language

@Dao
interface BibleDao {

    @Query("SELECT * FROM bibles")
    fun getAll(): List<BibleSummary>

    @Query("SELECT * FROM bibles where id=:id")
    fun getById(id: String): BibleSummary

    @Insert
    fun insert(bibleSummary: BibleSummary)

    @Update
    fun update(bibleSummary: BibleSummary)

    @Delete
    fun delete(bibleSummary: BibleSummary)

    @Query("SELECT * FROM countries")
    fun getAllCountries(): List<Country>

    @Insert
    fun insertCountry(country: Country)

    @Update
    fun updateCountry(country: Country)

    @Delete
    fun deleteCountry(country: Country)

    @Query("SELECT * FROM languages")
    fun getAllLanguages(): List<Language>

    @Insert
    fun insertLanguage(language: Language)

    @Update
    fun updateLanguage(language: Language)

    @Delete
    fun deleteLanguage(language: Language)
}