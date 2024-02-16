package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.Book
import de.domjos.gift_app.model.Passage
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PassageDaoTest {
    private lateinit var passageDao: PassageDao
    private lateinit var db: AppDatabase

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.passageDao = this.db.passageDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testPassage() {
        val summary = Passage("1", "1", "test", "Test", "Test", 0, "")

        val bibles = passageDao.getAll()
        assertEquals(0, bibles.size)
        passageDao.insert(summary)
        var bible = passageDao.getById("1")
        assertEquals("Test", bible.content)
        summary.content = "Test2"
        passageDao.update(summary)
        bible = passageDao.getById("1")
        assertEquals("Test2", bible.content)
        passageDao.delete(bible)
        assertEquals(0, passageDao.getAll().size)
    }
}