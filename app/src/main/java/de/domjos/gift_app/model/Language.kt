package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "languages",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_language_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Language(var id: String, var name: String, var nameLocal: String, var script: String, var scriptDirection: String)
