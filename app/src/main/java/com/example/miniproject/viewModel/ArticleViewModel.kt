package com.example.miniproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.repository.ArticleRepository
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private var articleRepository: ArticleRepository) : ViewModel() {

    fun callApiAndSaveInDB(sourceId: String?) {
        articleRepository.getArticleListFromWebAndInsertInDB(sourceId)
    }

    fun getArticles(id: String?): LiveData<List<ArticleModel?>?>? {
        return articleRepository.getArticleList(id)
    }

}