package de.domjos.gift_app.services

data class BibleAPIException(val code: Int, val msg: String) : Exception(msg)