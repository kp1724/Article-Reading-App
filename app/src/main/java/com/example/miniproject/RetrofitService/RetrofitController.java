package com.example.miniproject.RetrofitService;

import com.example.miniproject.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {
    private static final String API_BASE_URL = "https://newsapi.org/";
    private static Retrofit retrofit;
    private static final Object LOCK = new Object();

    private RetrofitController(){}

    private Retrofit getRetrofitController() {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.NewsApiKey)
                        .build();

                Request request = original.newBuilder()
                        .url(url).build();
                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(ServiceClient.baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (LOCK) {
                retrofit = new RetrofitController().getRetrofitController();
            }
        }
        return retrofit;
    }

}
