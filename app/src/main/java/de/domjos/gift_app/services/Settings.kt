package de.domjos.gift_app.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class Settings(context: Context) {
    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences("GiftApp", MODE_PRIVATE)
    }

    fun <T> getSetting(item: String, def: T) : T {
        return when(def) {
            is Int -> (pref.getInt(item, def) as T)
            is String -> (pref.getString(item, def) as T)
            is Boolean -> (pref.getBoolean(item, def) as T)
            is Long -> (pref.getLong(item, def) as T)
            is Float -> (pref.getFloat(item, def) as T)
            else -> def
        }
    }

    fun <T> saveSetting(item: String, value: T) {
        val editor = pref.edit()
        when(value) {
            is Int -> editor.putInt(item, value)
            is String -> editor.putString(item, value)
            is Boolean -> editor.putBoolean(item, value)
            is Long -> editor.putLong(item, value)
            is Float -> editor.putFloat(item, value)
        }
        editor.apply()
    }

    fun reset() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}