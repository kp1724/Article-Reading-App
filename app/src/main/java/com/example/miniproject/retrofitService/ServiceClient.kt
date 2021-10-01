package com.example.miniproject.retrofitService

import com.example.miniproject.model.AbstractResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceClient {
    @get:GET("/v2/sources")
    val sourcesList: Call<AbstractResponse?>?

    @GET("/v2/everything")
    fun getArticlesList(@Query("sources") sourceId: String?): Call<AbstractResponse?>?

    companion object {
        const val baseUrl = "https://newsapi.org"
    }
}