package de.domjos.gift_app.database

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.room.Room
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.services.BibleAPIService

class BibleService {
    private val db: AppDatabase
    private val service: BibleAPIService
    private val cm: ConnectivityManager
    private val isNetworkConnected: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.cm.getNetworkCapabilities(this.cm.activeNetwork)
                .isNetworkCapabilitiesValid()
        } else {
            this.cm.activeNetworkInfo?.isConnected ?: false
        }

    constructor(context: Context) {
        this.db = Room
            .databaseBuilder(context, AppDatabase::class.java, "bibleDatabase")
            .build()
        this.service = BibleAPIService()

        this.cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getBibles(): List<BibleSummary>? {
        return try {
            if(this.isNetworkConnected) {
                val response = this.service.getBibles(true, BibleAPIService.Language.German, "", "", "")
                response?.data?.toList()
            } else {
                this.db.bibleDao().getAll()
            }
        } catch (_: Exception) {
            null
        }
    }



    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true
        else -> false
    }
}