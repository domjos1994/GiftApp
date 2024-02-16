package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(
    tableName = "sections",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_sections_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Section(
    var id: String, var bibleId: String, var bookId: String, var chapterId: String,
    var title: String, var content: String, var verseCount: Int, var firstVerseId: String,
    var lastVerseId: String, var firstVerseOrgId: String, var lastVerseOrgId: String,
    var copyright: String, @Ignore var next: SectionSummary?, @Ignore var previous: SectionSummary?) {

    constructor(
        id: String, bibleId: String, bookId: String, chapterId: String,
        title: String, content: String, verseCount: Int, firstVerseId: String,
        lastVerseId: String, firstVerseOrgId: String, lastVerseOrgId: String,
        copyright: String)
    : this(id, bibleId, bookId, chapterId, title, content, verseCount, firstVerseId, lastVerseId,
        firstVerseOrgId, lastVerseOrgId, copyright, null, null)
}