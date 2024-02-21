package de.domjos.gift_app.database

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.room.Room
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.model.Book
import de.domjos.gift_app.model.Chapter
import de.domjos.gift_app.model.ChapterSummary
import de.domjos.gift_app.model.Passage
import de.domjos.gift_app.model.Verse
import de.domjos.gift_app.services.BibleAPIService
import de.domjos.gift_app.services.TaskRunner
import java.io.Console
import java.util.concurrent.Callable

class BibleService(private val context: Context) {
    private val db: AppDatabase
    private val service: BibleAPIService
    private val cm: ConnectivityManager

    private val isNetworkConnected: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.cm.getNetworkCapabilities(this.cm.activeNetwork)
                .isNetworkCapabilitiesValid()
        } else {
            this.cm.activeNetworkInfo?.isConnected ?: false
        }

    init {
        this.db = Room
            .databaseBuilder(context, AppDatabase::class.java, "bibleDatabase")
            .build()
        this.service = BibleAPIService()
        this.cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getBibles(callback: TaskRunner.Callback<List<BibleSummary>?>) {
        try {
            val callable = Callable {
                if(this.isNetworkConnected) {
                    val response = this.service.getBibles(true, BibleAPIService.Language.German, "", "", "")
                    response?.data?.toList()
                } else {
                    this.db.bibleDao().getAll()
                }
            }
            val taskRunner = TaskRunner()
            taskRunner.executeAsync(callable, callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    fun getBooks(callback: TaskRunner.Callback<List<Book>?>, id: String) {
        try {
            val callable = Callable {
                if(this.isNetworkConnected) {
                    val response = this.service.getBooks(id,
                        includeChapters = false,
                        includeChaptersAndSections = false
                    )
                    response?.data?.toList()
                } else {
                    this.db.bookDao().getByBible(id)
                }
            }
            val taskRunner = TaskRunner()
            taskRunner.executeAsync(callable, callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    fun getChapters(callback: TaskRunner.Callback<List<ChapterSummary>?>, id: String, bookId: String) {
        try {
            val callable = Callable {
                if(this.isNetworkConnected) {
                    val response = this.service.getChapters(id, bookId)
                    response?.data?.toList()
                } else {
                    this.db.chapterDao().getById(id, bookId)
                }
            }
            val taskRunner = TaskRunner()
            taskRunner.executeAsync(callable, callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }


    fun getChapter(callback: TaskRunner.Callback<Chapter?>, id: String, chapterId: String) {
        try {
            val callable = Callable {
                if(this.isNetworkConnected) {
                    val response = this.service.getChapter(id, chapterId, BibleAPIService.FORMAT.HTML,
                        notes = true,
                        titles = true,
                        chapterNumbers = true,
                        verseNumbers = true,
                        verseSpans = true,
                        parallels = ""
                    )
                    response?.data
                } else {
                    this.db.chapterDao().getById(chapterId)
                }
            }
            val taskRunner = TaskRunner()
            taskRunner.executeAsync(callable, callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    fun saveData(callback: TaskRunner.Callback<Unit?>, chapter: Chapter, chapterSummary: ChapterSummary, book: Book, bibleSummary: BibleSummary) {
        val callable = Callable {
            try {
                if(this.db.chapterDao().getAll().count { it.id == chapter.id } ==0) {
                    this.db.chapterDao().insert(chapter)
                }
                if(this.db.chapterDao().getAllSummaries().count { it.id == chapterSummary.id } ==0) {
                    this.db.chapterDao().insertSummary(chapterSummary)
                }
                if(this.db.bookDao().getAll().count { it.id == book.id } ==0) {
                    this.db.bookDao().insert(book)
                }
                if(this.db.bibleDao().getAll().count { it.id == bibleSummary.id } ==0) {
                    if(bibleSummary.relatedDbl == "") {
                        bibleSummary.relatedDbl = "Test"
                    }
                    this.db.bibleDao().insert(bibleSummary)
                }
            } catch (_: Exception) { }
        }
        val taskRunner = TaskRunner()
        taskRunner.executeAsync(callable, callback)
    }

    fun search(callback: TaskRunner.Callback<List<Verse>?>, id: String, query: String) {
        try {
            val callable = Callable {
                if(this.isNetworkConnected) {
                    val response = this.service.search(id, query, 10, 0, BibleAPIService.SORTING.RELEVANCE, "")
                    val results = response?.data?.verses?.toList()
                    val lst = ArrayList<Verse>()
                    results?.forEach {item ->
                        this.service.getVerse(item.bibleId, item.id, BibleAPIService.FORMAT.HTML,
                            notes = true,
                            titles = true,
                            chapterNumbers = true,
                            verseNumbers = true,
                            verseSpans = true,
                            parallels = "",
                            orgId = false
                        )?.data?.let {
                        lst.add(
                            it
                        )
                    } }
                    lst.forEach {item ->
                        if(this.db.verseDao().getAll().none { it.id == item.id }) this.db.verseDao().insert(item)
                    }
                    lst
                } else {
                    this.db.verseDao().getByQuery(query, id)
                }
            }
            val taskRunner = TaskRunner()
            taskRunner.executeAsync(callable, callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }


    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true
        else -> false
    }
}