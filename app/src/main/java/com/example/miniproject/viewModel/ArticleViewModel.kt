package com.example.miniproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.repository.ArticleRepository

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val articleRepository: ArticleRepository
    fun callApiAndSaveInDB(sourceId: String?) {
        articleRepository.getArticleListFromWebAndInsertInDB(sourceId)
    }

    fun getArticles(id: String?): LiveData<List<ArticleModel?>?>? {
        return articleRepository.getArticleList(id)
    }

    init {
        articleRepository = ArticleRepository(application)
    }
}