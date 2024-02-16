package de.domjos.gift_app.services

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BibleAPIServiceTest {
    private lateinit var api: BibleAPIService

    @Before
    fun useAppContext() {
        api = BibleAPIService()
    }

    @Test
    fun testGetBible() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bibleResponse = api.getBible(bibleSummary.id)
        assertNotNull(bibleResponse)
    }

    @Test
    fun testGetBook() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)
        val book = bookSummaryResponse?.data?.get(0)
        val bookSum = book?.id?.let { api.getBook(bibleSummary.id, it, true) }
        assertNotNull(bookSum)
    }

    @Test
    fun testGetChapters() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)
        val chaptersResponse =
            bookSummaryResponse?.data?.get(0)?.id?.let { api.getChapters(bibleSummary.id, it) }
        assertNotNull(chaptersResponse)
        val chapterId = chaptersResponse?.data?.get(0)?.id
        val chapterResponse = chapterId?.let {
            api.getChapter(bibleSummary.id,
                it,BibleAPIService.FORMAT.TEXT,
                notes = true,
                titles = true,
                chapterNumbers = true,
                verseNumbers = true,
                verseSpans = true,
                parallels = ""
            )
        }
        assertNotNull(chapterResponse)
    }

    @Test
    fun testGetSections() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)
        val bookId = bookSummaryResponse?.data?.get(0)?.id
        val chaptersResponse = bookId?.let { api.getChapters(bibleSummary.id, it) }
        assertNotNull(chaptersResponse)
        val bibleId = bibleSummary.id

        var sectionsResponse = bookId?.let { api.getSectionsByBook(bibleId, it) }
        assertNotNull(sectionsResponse)
        sectionsResponse = api.getSectionsByChapter(bibleId, "MAT.1")
        assertNotNull(sectionsResponse)
        val sectionId = sectionsResponse?.data?.get(0)?.id

        val sectionResponse = sectionId?.let {
            api.getSection(bibleId, it, BibleAPIService.FORMAT.TEXT,
                notes = true,
                titles = true,
                chapterNumbers = true,
                verseNumbers = true,
                verseSpans = true,
                parallels = ""
            )
        }
        assertNotNull(sectionResponse)
    }

    @Test
    fun testGetPassages() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)
        val bookId = bookSummaryResponse?.data?.get(0)?.id
        val chaptersResponse = bookId?.let { api.getChapters(bibleSummary.id, it) }
        assertNotNull(chaptersResponse)
        val chapterId = chaptersResponse?.data?.get(0)?.id
        val bibleId = bibleSummary.id

        val passageResponse = chapterId?.let {
            api.getPassage(bibleId,
                "MAT.1", BibleAPIService.FORMAT.TEXT,
                notes = true,
                titles = true,
                chapterNumbers = true,
                verseNumbers = true,
                verseSpans = true,
                parallels = "",
                orgId = false
            )
        }
        assertNotNull(passageResponse)
    }

    @Test
    fun testGetVerses() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)
        val bookId = bookSummaryResponse?.data?.get(0)?.id
        val chaptersResponse = bookId?.let { api.getChapters(bibleSummary.id, it) }
        assertNotNull(chaptersResponse)
        val chapterId = chaptersResponse?.data?.get(0)?.id
        val bibleId = bibleSummary.id

        val versesResponse = chapterId?.let { api.getVerses(bibleId, it) }
        assertNotNull(versesResponse)

        val verseResponse = versesResponse?.data?.get(0)?.id?.let {
            api.getVerse(
                bibleId, it, BibleAPIService.FORMAT.TEXT,
                notes = true,
                titles = true,
                chapterNumbers = true,
                verseNumbers = true,
                verseSpans = true,
                parallels = "",
                orgId = true
            )
        }
        assertNotNull(verseResponse)
    }

    @Test
    fun testSearch() {
        val bibleSummaryResponse = api.getBibles(false)
        assertNotNull(bibleSummaryResponse)
        val bibleSummary = bibleSummaryResponse!!.data[0]
        val bookSummaryResponse = api.getBooks(bibleSummary.id,
            includeChapters = true,
            includeChaptersAndSections = true
        )
        assertNotNull(bookSummaryResponse)

        val resp = api.search(bibleSummary.id, "J*", 10, 0, BibleAPIService.SORTING.RELEVANCE, "")
        assertNotNull(resp)
    }
}