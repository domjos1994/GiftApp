package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "verses",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_verse_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Verse(
    var id: String, var orgId: String, var bibleId: String, var bookId: String,
    var chapterId: String, var content: String, var reference: String, var verseCount: Int,
    var copyright: String)
