package de.domjos.gift_app.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Settings {
    private val pref: SharedPreferences

    constructor(context: Context) {
        pref = context.getSharedPreferences("GiftApp", MODE_PRIVATE)
    }

    fun getSetting(item: String, def: Int) : Int {
        return pref.getInt(item, def)
    }

    fun saveSetting(item: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(item.toString(), value)
        editor.apply()
    }
}