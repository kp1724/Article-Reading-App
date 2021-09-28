package com.example.miniproject.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.webview);
        TextView noInternetMessage = findViewById(R.id.no_internet_available_message);
        ArticleModel articleModel = (ArticleModel) getIntent().getSerializableExtra("Article_Model");
        if (articleModel != null && isNetworkAvailable()) {
            webView.loadUrl(articleModel.getUrl());
        } else {
            webView.setVisibility(View.GONE);
            noInternetMessage.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
