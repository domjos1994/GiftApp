package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.model.Country
import de.domjos.gift_app.model.Language
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class BibleDaoTest {
    private lateinit var bibleDao: BibleDao
    private lateinit var db: AppDatabase
    private lateinit var countryDummy: Country
    private lateinit var languageDummy: Language

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.bibleDao = this.db.bibleDao()

        this.countryDummy = Country("1", "Germany", "Deutschland")
        this.languageDummy = Language("1", "German", "Deutsch", "", "")

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testCountry() {
        var countries = bibleDao.getAllCountries()
        assertEquals(0, countries.size)
        bibleDao.insertCountry(countryDummy)
        countries = bibleDao.getAllCountries()
        assertEquals(1, countries.size)
        countryDummy.name = "Germany2"
        bibleDao.updateCountry(countryDummy)
        countries = bibleDao.getAllCountries()
        assertEquals(1, countries.size)
        bibleDao.deleteCountry(countryDummy)
        countries = bibleDao.getAllCountries()
        assertEquals(0, countries.size)
    }

    @Test
    fun testLanguage() {
        var language = bibleDao.getAllLanguages()
        assertEquals(0, language.size)
        bibleDao.insertLanguage(languageDummy)
        language = bibleDao.getAllLanguages()
        assertEquals(1, language.size)
        languageDummy.name = "German2"
        bibleDao.updateLanguage(languageDummy)
        language = bibleDao.getAllLanguages()
        assertEquals(1, language.size)
        bibleDao.deleteLanguage(languageDummy)
        language = bibleDao.getAllLanguages()
        assertEquals(0, language.size)
    }

    @Test
    fun testBible() {
        val summary = BibleSummary(
            "1", "1", "test", "test", languageDummy,
            arrayOf(countryDummy), "Test", "Test", "Test", "Test",
            "Test", "test", "Test", arrayOf()
        )

        val bibles = bibleDao.getAll()
        assertEquals(0, bibles.size)
        bibleDao.insert(summary)
        var bible = bibleDao.getById("1")
        assertEquals("Test", bible.name)
        summary.name = "Test2"
        bibleDao.update(summary)
        bible = bibleDao.getById("1")
        assertEquals("Test2", bible.name)
        bibleDao.delete(bible)
        assertEquals(0, bibleDao.getAll().size)
    }
}