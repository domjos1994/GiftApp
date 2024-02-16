package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.Chapter
import de.domjos.gift_app.model.ChapterSummary
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ChapterDaoTest {
    private lateinit var chapterDao: ChapterDao
    private lateinit var db: AppDatabase

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.chapterDao = this.db.chapterDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testChapter() {
        val summary = Chapter("1", "1", "1", "1", "Test", "Test", 1, "Test")

        val bibles = chapterDao.getAll()
        assertEquals(0, bibles.size)
        chapterDao.insert(summary)
        var bible = chapterDao.getById("1")
        assertEquals("Test", bible.content)
        summary.content = "Test2"
        chapterDao.update(summary)
        bible = chapterDao.getById("1")
        assertEquals("Test2", bible.content)
        chapterDao.delete(bible)
        assertEquals(0, chapterDao.getAll().size)
    }

    @Test
    fun testChapterSummary() {
        val summary = ChapterSummary("1", "1", "1", "1", "Test")

        val bibles = chapterDao.getAllSummaries()
        assertEquals(0, bibles.size)
        chapterDao.insertSummary(summary)
        var bible = chapterDao.getSummaryById("1")
        assertEquals("Test", bible.reference)
        summary.reference = "Test2"
        chapterDao.updateSummary(summary)
        bible = chapterDao.getSummaryById("1")
        assertEquals("Test2", bible.reference)
        chapterDao.deleteSummary(bible)
        assertEquals(0, chapterDao.getAll().size)
    }
}