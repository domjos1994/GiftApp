package de.domjos.gift_app.services

import com.google.gson.Gson
import de.domjos.gift_app.BuildConfig
import de.domjos.gift_app.model.json.BibleResponse
import de.domjos.gift_app.model.json.BibleSummaryResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class BibleAPIService {
    private val API_KEY: String = BuildConfig.BIBLE_API_KEY
    private val BASE_URL: String = BuildConfig.BIBLE_API_URL
    private val LANGUAGE: String = "language=deu"
    var client = OkHttpClient()

    init {
        client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("api-key", API_KEY)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
    }

    fun getBibles(full: Boolean): BibleSummaryResponse? {
        val details = if (full) "true" else "false"

        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL/v1/bibles?$LANGUAGE&include-full-details=$details").build()

        return getResult(request)
    }

    fun getBible(id: String) : BibleResponse? {
        val request = Request.Builder()
            .addHeader("api-key", API_KEY)
            .url("$BASE_URL/v1/bibles/$id").build()

        return getResult(request)
    }

    private inline fun <reified T> getResult(request: Request): T? {
        client.newCall(request).execute().use { call ->
            if(!call.isSuccessful) throw IOException("Unexpected code $call")

            val result = call.body!!.string()
            return Gson().fromJson(result, T::class.java)
        }
    }
}