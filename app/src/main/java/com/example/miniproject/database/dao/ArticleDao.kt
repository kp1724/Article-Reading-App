package com.example.miniproject.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniproject.model.ArticleModel

@Dao
interface ArticleDao {
    @Query("SELECT * FROM tblArticle")
    fun loadAllArticles(): LiveData<List<ArticleModel?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articleModels: List<ArticleModel?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleModel: ArticleModel?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(articleModel: ArticleModel?)

    @Query("DELETE FROM tblArticle")
    fun deleteAllArticles()

    @Delete
    fun deleteArticle(articleModel: ArticleModel?)

    @Query("SELECT * FROM tblArticle WHERE source = :id")
    fun getArticle(id: String?): LiveData<List<ArticleModel?>?>?
}