package de.domjos.gift_app.database.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.domjos.gift_app.database.AppDatabase
import de.domjos.gift_app.model.Book
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class BookDaoTest {
    private lateinit var bookDao: BookDao
    private lateinit var db: AppDatabase

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.bookDao = this.db.bookDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        this.db.close()
    }

    @Test
    fun testBook() {
        val summary = Book("1", "1", "test", "Test", "Test")

        val bibles = bookDao.getAll()
        assertEquals(0, bibles.size)
        bookDao.insert(summary)
        var bible = bookDao.getById("1")
        assertEquals("Test", bible.name)
        summary.name = "Test2"
        bookDao.update(summary)
        bible = bookDao.getById("1")
        assertEquals("Test2", bible.name)
        bookDao.delete(bible)
        assertEquals(0, bookDao.getAll().size)
    }
}