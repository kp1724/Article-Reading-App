package com.example.miniproject.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.miniproject.Database.Controller.DatabaseController;
import com.example.miniproject.Model.AbstractResponse;
import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.RetrofitService.RetrofitController;
import com.example.miniproject.RetrofitService.ServiceClient;
import com.example.miniproject.Utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private final Context context;

    public ArticleRepository(Context context) {
        this.context = context;

    }

    public void getArticleListFromWebAndInsertInDB(String sourceId) {
        ServiceClient serviceClient = RetrofitController.getInstance().create(ServiceClient.class);
        serviceClient.getArticlesList(sourceId).enqueue(new Callback<AbstractResponse>() {
            @Override
            public void onResponse(Call<AbstractResponse> call, final Response<AbstractResponse> response) {
                if (response.body() != null && response.body().getStatus().equals("ok"))
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseController.getInstance(context).articleDao().deleteAllArticles();
                            DatabaseController.getInstance(context).articleDao().insertAllArticles(response.body().getArticles());
                        }
                    });
            }

            @Override
            public void onFailure(Call<AbstractResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<List<ArticleModel>> getArticleFromDb() {
        return DatabaseController.getInstance(context).articleDao().loadAllArticles();
    }

    public LiveData<List<ArticleModel>> getArticleList(String id){
        return DatabaseController.getInstance(context).articleDao().getArticle(id);
    }
}
