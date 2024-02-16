package de.domjos.gift_app.services

import com.google.gson.Gson
import de.domjos.gift_app.BuildConfig
import de.domjos.gift_app.model.json.BibleResponse
import de.domjos.gift_app.model.json.BibleSummaryResponse
import de.domjos.gift_app.model.json.BookResponse
import de.domjos.gift_app.model.json.BooksResponse
import de.domjos.gift_app.model.json.ChapterResponse
import de.domjos.gift_app.model.json.ChaptersResponse
import de.domjos.gift_app.model.json.PassageResponse
import de.domjos.gift_app.model.json.SearchResponse
import de.domjos.gift_app.model.json.SectionResponse
import de.domjos.gift_app.model.json.SectionsResponse
import de.domjos.gift_app.model.json.VerseResponse
import de.domjos.gift_app.model.json.VersesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

private const val API_KEY: String = BuildConfig.BIBLE_API_KEY
private const val BASE_URL: String = BuildConfig.BIBLE_API_URL
private const val BASE_PATH: String = "/v1/bibles"

class BibleAPIService {
    private var client = OkHttpClient()

    init {
        client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("api-key", API_KEY)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
    }

    fun getBibles(fullDetails: Boolean, language: Language = Language.None, abbreviation: String = "", name: String = "", ids: String = ""): BibleSummaryResponse? {
        val details = "?include-full-details=" + if (fullDetails) "true" else "false"
        val lang = if(lang(language)=="") "" else "&" + lang(language)
        val abbr = if(abbreviation=="") "" else "&$abbreviation"
        val namePath = if(name=="") "" else "&$name"
        val idsPath = if(ids=="") "" else "&$ids"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH$details$lang$abbr$namePath$idsPath")
            .build()

        return getResult(request)
    }

    fun getBible(id: String) : BibleResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH/$id").build()

        return getResult(request)
    }

    fun getBooks(id: String, includeChapters: Boolean, includeChaptersAndSections: Boolean) : BooksResponse? {
        val chapters = "?include-chapters=" + if (includeChapters) "true" else "false"
        val chaptersAndSections = "&include-chapters-and-sections=" + if (includeChaptersAndSections) "true" else "false"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH/$id/books$chapters$chaptersAndSections").build()

        return getResult(request)
    }

    fun getBook(id: String, bookId: String, includeChapters: Boolean) : BookResponse? {
        val chapters = "?include-chapters=" + if (includeChapters) "true" else "false"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH/$id/books/$bookId$chapters").build()

        return getResult(request)
    }

    fun getChapters(id: String, bookId: String) : ChaptersResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH/$id/books/$bookId/chapters").build()

        return getResult(request)
    }

    fun getChapter(
        id: String, chapterId: String, format: FORMAT, notes: Boolean,
        titles: Boolean, chapterNumbers: Boolean, verseNumbers: Boolean,
        verseSpans: Boolean, parallels: String) : ChapterResponse? {
        val form = form(format)
        val notesPath = "&include-notes=" + if (notes) "true" else "false"
        val titlesPath = "&include-titles=" + if (titles) "true" else "false"
        val chapterNumbersPath = "&include-chapter-numbers=" + if (chapterNumbers) "true" else "false"
        val verseNumbersPath = "&include-verse-numbers=" + if (verseNumbers) "true" else "false"
        val verseSpansPath = "&include-verse-spans=" + if (verseSpans) "true" else "false"
        val parallelsPath = if (parallels=="") "" else "&parallels=$parallels"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/chapters/$chapterId?" +
                        "$form$notesPath$titlesPath$chapterNumbersPath$verseNumbersPath" +
                        "$verseSpansPath$parallelsPath")
            .build()

        return getResult(request)
    }

    fun getSectionsByBook(id: String, bookId: String): SectionsResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/books/$bookId/sections")
            .build()

        return getResult(request)
    }

    fun getSectionsByChapter(id: String, chapterId: String): SectionsResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/chapters/$chapterId/sections")
            .build()

        return getResult(request)
    }

    fun getSection(
        id: String, sectionId: String, format: FORMAT, notes: Boolean,
        titles: Boolean, chapterNumbers: Boolean, verseNumbers: Boolean,
        verseSpans: Boolean, parallels: String) : SectionResponse? {
        val form = form(format)
        val notesPath = "&include-notes=" + if (notes) "true" else "false"
        val titlesPath = "&include-titles=" + if (titles) "true" else "false"
        val chapterNumbersPath = "&include-chapter-numbers=" + if (chapterNumbers) "true" else "false"
        val verseNumbersPath = "&include-verse-numbers=" + if (verseNumbers) "true" else "false"
        val verseSpansPath = "&include-verse-spans=" + if (verseSpans) "true" else "false"
        val parallelsPath = if (parallels=="") "" else "&parallels=$parallels"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/sections/$sectionId?" +
                        "$form$notesPath$titlesPath$chapterNumbersPath$verseNumbersPath" +
                        "$verseSpansPath$parallelsPath")
            .build()

        return getResult(request)
    }

    fun getPassage(
        id: String, passageId: String, format: FORMAT, notes: Boolean,
        titles: Boolean, chapterNumbers: Boolean, verseNumbers: Boolean,
        verseSpans: Boolean, parallels: String, orgId: Boolean) : PassageResponse? {
        val form = form(format)
        val notesPath = "&include-notes=" + if (notes) "true" else "false"
        val titlesPath = "&include-titles=" + if (titles) "true" else "false"
        val chapterNumbersPath = "&include-chapter-numbers=" + if (chapterNumbers) "true" else "false"
        val verseNumbersPath = "&include-verse-numbers=" + if (verseNumbers) "true" else "false"
        val verseSpansPath = "&include-verse-spans=" + if (verseSpans) "true" else "false"
        val parallelsPath = if (parallels=="") "" else "&parallels=$parallels"
        val orgIdPath = "&use-org-id=" + if(orgId) "true" else "false"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/passages/$passageId?" +
                        "$form$notesPath$titlesPath$chapterNumbersPath$verseNumbersPath" +
                        "$verseSpansPath$parallelsPath$orgIdPath")
            .build()

        return getResult(request)
    }

    fun getVerses(id: String, chapterId: String): VersesResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/chapters/$chapterId/verses")
            .build()

        return getResult(request)
    }

    fun getVerse(
        id: String, verseId: String, format: FORMAT, notes: Boolean,
        titles: Boolean, chapterNumbers: Boolean, verseNumbers: Boolean,
        verseSpans: Boolean, parallels: String, orgId: Boolean) : VerseResponse? {
        val form = form(format)
        val notesPath = "&include-notes=" + if (notes) "true" else "false"
        val titlesPath = "&include-titles=" + if (titles) "true" else "false"
        val chapterNumbersPath = "&include-chapter-numbers=" + if (chapterNumbers) "true" else "false"
        val verseNumbersPath = "&include-verse-numbers=" + if (verseNumbers) "true" else "false"
        val verseSpansPath = "&include-verse-spans=" + if (verseSpans) "true" else "false"
        val parallelsPath = if (parallels=="") "" else "&parallels=$parallels"
        val orgIdPath = "&use-org-id=" + if(orgId) "true" else "false"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url(
                "$BASE_URL$BASE_PATH/$id/verses/$verseId?" +
                        "$form$notesPath$titlesPath$chapterNumbersPath$verseNumbersPath" +
                        "$verseSpansPath$parallelsPath$orgIdPath")
            .build()

        return getResult(request)
    }

    fun search(
        id: String, query: String, limit: Int = 10, offset: Int = 0,
        sorting: SORTING, range: String): SearchResponse? {
        val rangePath = if(range=="") "" else "&range=$range"
        val path = "?query=$query&limit=$limit&offset=$offset$rangePath"
        val sortPath = "&" + sort(sorting)

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL$BASE_PATH/$id/search$path$sortPath")
            .build()

        return getResult(request)
    }


    private inline fun <reified T> getResult(request: Request): T? {
        client.newCall(request).execute().use { call ->
            if(!call.isSuccessful) throw BibleAPIException(call.code, "Unexpected code $call")

            val result = call.body!!.string()
            return Gson().fromJson(result, T::class.java)
        }
    }

    private fun lang(lang: Language): String {
        return when(lang) {
            Language.German -> "language=${lang.code}"
            Language.English -> "language=${lang.code}"
            else -> ""
        }
    }

    private fun form(format: FORMAT): String {
        return when(format) {
            FORMAT.HTML -> "content-type=${format.type}"
            FORMAT.JSON -> "content-type=${format.type}"
            FORMAT.TEXT -> "content-type=${format.type}"
        }
    }

    private fun sort(sorting: SORTING): String {
        return when(sorting) {
            SORTING.RELEVANCE -> "sort=${sorting.type}"
            SORTING.CANONICAL -> "sort=${sorting.type}"
            SORTING.REVERSE_CANONICAL -> "sort=${sorting.type}"
        }
    }

    enum class Language(val code: String) {
        German("deu"),
        English("eng"),
        None("")
    }

    enum class FORMAT(val type: String) {
        HTML("html"),
        JSON("json"),
        TEXT("text")
    }

    enum class SORTING(val type: String) {
        RELEVANCE("relevance"),
        CANONICAL("canonical"),
        REVERSE_CANONICAL("reverse-canonical")
    }
}