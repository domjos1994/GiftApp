package de.domjos.gift_app.services

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TaskRunner {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    interface Callback<R> {
        fun onComplete(result: DailyVerse.JsonVerse?)
    }

    fun <R> executeAsync(callable: DailyVerse?, callBack: Callback<R?>) {
        executor.execute {
            val result: DailyVerse.JsonVerse? = callable?.call()
            handler.post { callBack.onComplete(result) }
        }
    }
}