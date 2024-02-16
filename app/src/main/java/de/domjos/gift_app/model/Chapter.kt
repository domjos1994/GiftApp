package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(
    tableName = "chapters",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_chapter_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Chapter(
    var id: String, var bibleId: String, var number: String, var bookId: String,
    var content: String, var reference: String, var verseCount: Int,
    @Ignore var next: ChapterSummary?, @Ignore var previous: ChapterSummary?, var copyright: String) {

    constructor(
        id: String, bibleId: String, number: String, bookId: String,
        content: String, reference: String, verseCount: Int, copyright: String)
    : this(id, bibleId, number, bookId, content, reference, verseCount, null, null, copyright)
}
