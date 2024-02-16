package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.Verse
import de.domjos.gift_app.model.VerseSummary
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class VerseDaoTest {
    private lateinit var verseDao: VerseDao
    private lateinit var db: AppDatabase

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.verseDao = this.db.verseDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testChapter() {
        val summary = Verse(
            "1", "1", "1", "1", "Test", "Test", "", 0, ""
        )

        val bibles = this.verseDao.getAll()
        assertEquals(0, bibles.size)
        this.verseDao.insert(summary)
        var bible = this.verseDao.getById("1")
        assertEquals("Test", bible.content)
        summary.content = "Test2"
        this.verseDao.update(summary)
        bible = this.verseDao.getById("1")
        assertEquals("Test2", bible.content)
        this.verseDao.delete(bible)
        assertEquals(0, this.verseDao.getAll().size)
    }

    @Test
    fun testChapterSummary() {
        val summary = VerseSummary("1", "1", "1", "", "", "Test")

        val bibles = this.verseDao.getAllSummaries()
        assertEquals(0, bibles.size)
        this.verseDao.insertSummary(summary)
        var bible = this.verseDao.getSummaryById("1")
        assertEquals("Test", bible.reference)
        summary.reference = "Test2"
        this.verseDao.updateSummary(summary)
        bible = this.verseDao.getSummaryById("1")
        assertEquals("Test2", bible.reference)
        this.verseDao.deleteSummary(bible)
        assertEquals(0, this.verseDao.getAll().size)
    }
}