package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "chapter_summaries",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_chapter_summary_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class ChapterSummary(var id: String, var bibleId: String, var number: String, var bookId: String, var reference: String)
