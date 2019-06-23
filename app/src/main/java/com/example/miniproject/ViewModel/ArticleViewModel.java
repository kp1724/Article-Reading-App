package com.example.miniproject.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Repository.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository articleRepository;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        this.articleRepository = new ArticleRepository(application);
    }

    public void callApiAndSaveInDB(String sourceId){
        articleRepository.getArticleListFromWebAndInsertInDB(sourceId);
    }

    public LiveData<List<ArticleModel>> getArticles() {
        return articleRepository.getArticleFromDb();
    }

    public LiveData<List<ArticleModel>> getArticles(String id) {
        return articleRepository.getArticleList(id);
    }

}
