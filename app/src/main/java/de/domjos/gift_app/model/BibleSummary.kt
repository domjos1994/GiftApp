package de.domjos.gift_app.model

data class BibleSummary(
    val id: String, val dblId: String, val abbreviation: String, val abbreviationLocal: String,
    val language: Language, val countries: Array<Country>, val name: String, val nameLocal: String,
    val description: String, val descriptionLocal: String, val relatedDbl: String, val type: String,
    val updatedAt: String, val audioBibles: Array<AudioBibleSummary>) {
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
}