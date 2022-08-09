package de.domjos.gift_app.services

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.Callable

class DailyVerse : Callable<DailyVerse.JsonVerse> {

    override fun call(): JsonVerse {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://beta.ourmanna.com/api/v1/get?format=json&order=daily")
            .get()
            .addHeader("Accept", "application/json")
            .build()

        val response = client.newCall(request).execute()
        val content = response.body!!.string()

        val gson = Gson()
        return gson.fromJson(content, JsonVerse().javaClass);
    }

    class JsonVerse {
        var verse: Verse? = null

        class Verse {
            var notice: String = ""
            var details: Details? = null

            class Details {
                var text: String = ""
                var reference: String = ""
                var version: String = ""
            }
        }
    }
}