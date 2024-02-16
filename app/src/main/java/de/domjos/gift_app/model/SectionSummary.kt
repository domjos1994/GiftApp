package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "section_summaries",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_section_summary_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class SectionSummary(
    var id: String, var bibleId: String, var bookId: String, var title: String,
    var firstVerseId: String, var lastVerseId: String,
    var firstVerseOrgId: String, var lastVerseOrgId: String)
