package com.example.miniproject.RetrofitService;

import com.example.miniproject.Model.AbstractResponse;
import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceClient {
    String baseUrl = "https://newsapi.org";

    @GET("/v2/sources")
    Call<AbstractResponse> getSourcesList(/*@Query("apiKey") String apiKey*/);

    @GET("/v2/everything")
    Call<AbstractResponse> getArticlesList(@Query("sources") String sourceId/*, @Query("apiKey") String apiKey*/);
}
