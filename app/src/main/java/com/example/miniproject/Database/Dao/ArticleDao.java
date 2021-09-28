package com.example.miniproject.Database.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.miniproject.Model.ArticleModel;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM tblArticle")
    LiveData<List<ArticleModel>> loadAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllArticles(List<ArticleModel> articleModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(ArticleModel articleModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(ArticleModel articleModel);

    @Query("DELETE FROM tblArticle")
    void deleteAllArticles();

    @Delete
    void deleteArticle(ArticleModel articleModel);

    @Query("SELECT * FROM tblArticle WHERE source = :id")
    LiveData<List<ArticleModel>> getArticle(String id);
}
