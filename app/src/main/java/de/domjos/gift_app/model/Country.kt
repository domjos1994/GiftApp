package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "countries",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_country_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Country(var id: String, var name: String, var nameLocal: String)
