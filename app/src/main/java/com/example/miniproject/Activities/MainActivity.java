package com.example.miniproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Adapters.SourceRecyclerViewAdapter;
import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.R;
import com.example.miniproject.ViewModel.SourceViewModel;
import com.example.miniproject.fragments.ArticleFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ProgressBar progressBar;
    private FrameLayout fragmentContainer;
    private RecyclerView sourceRecyclerView;
    private SourceRecyclerViewAdapter sourceRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initViews();
        if (savedInstanceState == null) {
            sourceRecyclerViewAdapter = new SourceRecyclerViewAdapter(mContext);
            sourceRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            sourceRecyclerView.setAdapter(sourceRecyclerViewAdapter);
            setUpSourcesList();
        }
    }

    private void setUpSourcesList() {
        showProgressBar();
        setUpViewModel();
    }

    private void setUpViewModel() {
        SourceViewModel sourceViewModel = new SourceViewModel(getApplication());
        LiveData<List<SourceModel>> sourceModelList = sourceViewModel.getSourceList();
        sourceViewModel.callApiAndSaveInDB();
        hideProgressBarAndShowSourceList();
        sourceModelList.observe((LifecycleOwner) mContext, new Observer<List<SourceModel>>() {
            @Override
            public void onChanged(@Nullable List<SourceModel> sourceModels) {
                sourceRecyclerViewAdapter.setSourcesList(sourceModels);
                sourceRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void hideProgressBarAndShowSourceList() {
        progressBar.setVisibility(View.GONE);
        sourceRecyclerView.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        sourceRecyclerView.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.GONE);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_circular);
        fragmentContainer = findViewById(R.id.fragment_container);
        sourceRecyclerView = findViewById(R.id.sources_recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sources_cardview_parent:
                SourceModel model = (SourceModel) v.getTag();
                showArticlesListFragment(model);
                break;
            case R.id.article_cardview_parent:
                ArticleModel articleModel = (ArticleModel) v.getTag();
                showArticleActivity(articleModel);
        }
    }

    private void showArticleActivity(ArticleModel articleModel) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("Article_Model", articleModel);
        startActivity(intent);
    }

    private void showArticlesListFragment(SourceModel model) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ArticleFragment articleFragment = ArticleFragment.newInstance(model);
        ft.add(R.id.fragment_container, articleFragment);
        ft.addToBackStack("articleFragment");
        ft.commit();
        showFragmentContainer();
    }

    private void showFragmentContainer() {
        progressBar.setVisibility(View.GONE);
        sourceRecyclerView.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentContainer.getVisibility() == View.VISIBLE) {
            hideProgressBarAndShowSourceList();
            getSupportFragmentManager().popBackStack();
        } else
            finish();
    }
}
