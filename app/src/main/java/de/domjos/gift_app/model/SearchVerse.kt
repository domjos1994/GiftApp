package de.domjos.gift_app.model

data class SearchVerse(
    var id: String, var orgId: String, var bibleId: String, var bookId: String,
    var chapterId: String, var text: String, var reference: String)
