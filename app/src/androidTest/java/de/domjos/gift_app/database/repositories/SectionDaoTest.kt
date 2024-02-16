package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.Section
import de.domjos.gift_app.model.SectionSummary
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SectionDaoTest {
    private lateinit var sectionDao: SectionDao
    private lateinit var db: AppDatabase

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.sectionDao = this.db.sectionDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testChapter() {
        val summary = Section(
            "1", "1", "1", "1", "Test", "Test",
            1, "Test", "", "", "", ""
        )

        val bibles = this.sectionDao.getAll()
        assertEquals(0, bibles.size)
        this.sectionDao.insert(summary)
        var bible = this.sectionDao.getById("1")
        assertEquals("Test", bible.content)
        summary.content = "Test2"
        this.sectionDao.update(summary)
        bible = this.sectionDao.getById("1")
        assertEquals("Test2", bible.content)
        this.sectionDao.delete(bible)
        assertEquals(0, this.sectionDao.getAll().size)
    }

    @Test
    fun testChapterSummary() {
        val summary = SectionSummary("1", "1", "1", "Test", "", "", "", "")

        val bibles = this.sectionDao.getAllSummaries()
        assertEquals(0, bibles.size)
        this.sectionDao.insertSummary(summary)
        var bible = this.sectionDao.getSummaryById("1")
        assertEquals("Test", bible.title)
        summary.title = "Test2"
        this.sectionDao.updateSummary(summary)
        bible = this.sectionDao.getSummaryById("1")
        assertEquals("Test2", bible.title)
        this.sectionDao.deleteSummary(bible)
        assertEquals(0, sectionDao.getAll().size)
    }
}