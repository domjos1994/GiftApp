package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "passages",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_passage_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Passage(
    var id: String, var bibleId: String, var orgId: String, var content: String,
    var references: String, var verseCount: Int, var copyright: String)
