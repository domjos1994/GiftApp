package de.domjos.gift_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.domjos.gift_app.database.repositories.BibleDao
import de.domjos.gift_app.database.repositories.BookDao
import de.domjos.gift_app.database.repositories.ChapterDao
import de.domjos.gift_app.database.repositories.PassageDao
import de.domjos.gift_app.database.repositories.SectionDao
import de.domjos.gift_app.database.repositories.VerseDao
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.model.Book
import de.domjos.gift_app.model.Chapter
import de.domjos.gift_app.model.ChapterSummary
import de.domjos.gift_app.model.Country
import de.domjos.gift_app.model.Language
import de.domjos.gift_app.model.Passage
import de.domjos.gift_app.model.Section
import de.domjos.gift_app.model.SectionSummary
import de.domjos.gift_app.model.Verse
import de.domjos.gift_app.model.VerseSummary

@Database(entities = [
    BibleSummary::class, Book::class, Chapter::class,
    ChapterSummary::class, Country::class, Language::class,
    Passage::class, Section::class, SectionSummary::class,
    Verse::class, VerseSummary::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao
    abstract fun bookDao(): BookDao
    abstract fun chapterDao(): ChapterDao
    abstract fun passageDao(): PassageDao
    abstract fun sectionDao(): SectionDao
    abstract fun verseDao(): VerseDao
}