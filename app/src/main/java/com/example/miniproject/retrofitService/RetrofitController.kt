package com.example.miniproject.retrofitService

import android.util.Log
import com.example.miniproject.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitController private constructor() {
    private val retrofitController: Retrofit?
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.NewsApiKey)
                        .build()
                    Log.d("Url", url.toString())
                    val request = original.newBuilder()
                        .url(url).build()
                    return chain.proceed(request)
                }
            })
            retrofit = Retrofit.Builder()
                .baseUrl(ServiceClient.baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    companion object {
        private var retrofit: Retrofit? = null
        private val LOCK = Any()
        val instance: Retrofit?
            get() {
                if (retrofit == null) {
                    synchronized(LOCK) { retrofit = RetrofitController().retrofitController }
                }
                return retrofit
            }
    }
}