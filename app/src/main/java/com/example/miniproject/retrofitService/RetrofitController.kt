package com.example.miniproject.retrofitService

import android.util.Log
import com.example.miniproject.BuildConfig
import com.example.miniproject.extension.KotlinExtension.BooleanExtension.isFalse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitController private constructor() {
    private val retrofitController: Retrofit
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.NewsApiKey)
                    .build()
                Log.d("Url", url.toString())
                val request = original.newBuilder()
                    .url(url).build()
                chain.proceed(request)
            })
            retrofit = Retrofit.Builder()
                .baseUrl(ServiceClient.baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    companion object {
        private lateinit var retrofit: Retrofit
        private val LOCK = Any()
        val instance: Retrofit
            get() {
                if (this::retrofit.isInitialized.isFalse()) {
                    synchronized(LOCK) { retrofit = RetrofitController().retrofitController }
                }
                return retrofit
            }
    }
}