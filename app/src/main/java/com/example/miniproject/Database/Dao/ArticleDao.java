package com.example.miniproject.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
