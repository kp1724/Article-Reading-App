package com.example.miniproject.repository

import androidx.lifecycle.LiveData
import com.example.miniproject.database.controller.DatabaseController
import com.example.miniproject.model.AbstractResponse
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.retrofitService.ServiceClient
import com.example.miniproject.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val databaseController: DatabaseController,
    private val serviceClient: ServiceClient
) {
    fun getArticleListFromWebAndInsertInDB(sourceId: String?) {
        serviceClient.getArticlesList(sourceId)?.enqueue(object : Callback<AbstractResponse?> {
            override fun onResponse(
                call: Call<AbstractResponse?>,
                response: Response<AbstractResponse?>
            ) {
                if (response.body() != null && (response.body()?.status == "ok"))
                    AppExecutors.instance?.diskIO?.execute {
                        databaseController.articleDao().deleteAllArticles()
                        databaseController.articleDao()
                            .insertAllArticles(response.body()?.articles)
                    }
            }

            override fun onFailure(call: Call<AbstractResponse?>, t: Throwable) {}
        })
    }

    fun getArticleList(id: String?): LiveData<List<ArticleModel?>?>? {
        return databaseController.articleDao().getArticle(id)
    }
}