package com.example.miniproject.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor(//an executor to run commands on disk
    val diskIO: Executor
) {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        //A singleton class
        private const val threadPoolSize = 3
        private val LOCK = Any()
        private var sInstance: AppExecutors? = null
        val instance: AppExecutors?
            get() {
                if (sInstance == null) {
                    synchronized(LOCK) {
                        sInstance = AppExecutors(
                            Executors.newSingleThreadExecutor()
                        )
                    }
                }
                return sInstance
            }
    }
}