package de.domjos.gift_app.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(
    tableName = "bibles",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_bible_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class BibleSummary(
    var id: String, var dblId: String, var abbreviation: String, var abbreviationLocal: String,
    @Ignore var language: Language?, @Ignore var countries: Array<Country>?, var name: String, var nameLocal: String,
    var description: String, var descriptionLocal: String, @ColumnInfo(defaultValue = "") var relatedDbl: String?, var type: String,
    var updatedAt: String, @Ignore var audioBibles: Array<AudioBibleSummary>?) {

    constructor(
        id: String, dblId: String, abbreviation: String, abbreviationLocal: String,
        name: String, nameLocal: String, description: String, descriptionLocal: String,
        relatedDbl: String, type: String, updatedAt: String) :
            this(id, dblId, abbreviation, abbreviationLocal, null,
                arrayOf(), name, nameLocal, description, descriptionLocal, relatedDbl, type, updatedAt, arrayOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BibleSummary

        if (id != other.id) return false
        if (dblId != other.dblId) return false
        if (abbreviation != other.abbreviation) return false
        if (abbreviationLocal != other.abbreviationLocal) return false
        if (language != other.language) return false
        if (!countries.contentEquals(other.countries)) return false
        if (name != other.name) return false
        if (nameLocal != other.nameLocal) return false
        if (description != other.description) return false
        if (descriptionLocal != other.descriptionLocal) return false
        if (relatedDbl != other.relatedDbl) return false
        if (type != other.type) return false
        if (updatedAt != other.updatedAt) return false
        return audioBibles.contentEquals(other.audioBibles)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dblId.hashCode()
        result = 31 * result + abbreviation.hashCode()
        result = 31 * result + abbreviationLocal.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + countries.contentHashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nameLocal.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + descriptionLocal.hashCode()
        result = 31 * result + relatedDbl.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + audioBibles.contentHashCode()
        return result
    }

    override fun toString(): String {
        return name
    }
}