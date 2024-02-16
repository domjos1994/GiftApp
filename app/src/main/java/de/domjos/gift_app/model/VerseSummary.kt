package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "verse_summaries",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_verse_summary_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class VerseSummary(
    var id: String, var orgId: String, var bibleId: String,
    var bookId: String, var chapterId: String, var reference: String)
