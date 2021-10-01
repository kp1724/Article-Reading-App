package com.example.miniproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.miniproject.database.controller.DatabaseController.Companion.getInstance
import com.example.miniproject.model.AbstractResponse
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.retrofitService.RetrofitController
import com.example.miniproject.retrofitService.ServiceClient
import com.example.miniproject.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository constructor(private val context: Context) {
    fun getArticleListFromWebAndInsertInDB(sourceId: String?) {
        val serviceClient: ServiceClient? = RetrofitController.instance?.create(
            ServiceClient::class.java
        )
        serviceClient?.getArticlesList(sourceId)?.enqueue(object : Callback<AbstractResponse?> {
            override fun onResponse(
                call: Call<AbstractResponse?>,
                response: Response<AbstractResponse?>
            ) {
                if (response.body() != null && (response.body()!!.status == "ok"))
                    AppExecutors.instance?.diskIO?.execute {
                        getInstance(context)!!.articleDao()!!.deleteAllArticles()
                        getInstance(context)!!.articleDao()!!
                            .insertAllArticles(response.body()!!.articles)
                    }
            }

            override fun onFailure(call: Call<AbstractResponse?>, t: Throwable) {}
        })
    }

    fun getArticleFromDb() = getInstance(context)!!.articleDao()!!.loadAllArticles()

    fun getArticleList(id: String?): LiveData<List<ArticleModel?>?>? {
        return getInstance(context)!!.articleDao()!!.getArticle(id)
    }
}