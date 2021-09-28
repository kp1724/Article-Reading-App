package com.example.miniproject.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Adapters.ArticleRecyclerViewAdapter;
import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.R;
import com.example.miniproject.ViewModel.ArticleViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {
    private static final String SOURCE_MODEL = "source_model";
    private SourceModel sourceModel;
    private ProgressBar progressBar;
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    private RecyclerView articleRecyclerView;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(SourceModel sourceModel) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(SOURCE_MODEL, sourceModel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sourceModel = (SourceModel) getArguments().getSerializable(SOURCE_MODEL);
            articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(getActivity());
        }
    }

    private void setUpSourcesList() {
        showProgressBar();
        setUpViewModel();
    }

    private void setUpViewModel() {
        LiveData<List<ArticleModel>> articleModels = null;
        ArticleViewModel articleViewModel = new ArticleViewModel(getActivity().getApplication());
        articleViewModel.callApiAndSaveInDB(sourceModel.getId());
        articleModels = articleViewModel.getArticles(sourceModel.getId());
        hideProgressBar();
        articleModels.observe(this, new Observer<List<ArticleModel>>() {
            @Override
            public void onChanged(@Nullable List<ArticleModel> sourceModels) {
                articleRecyclerViewAdapter.setArticlesList(sourceModels);
                articleRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        articleRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        articleRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        articleRecyclerView = view.findViewById(R.id.articles_recyclerView);
        progressBar = view.findViewById(R.id.progress_circular);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleRecyclerView.setAdapter(articleRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sourceModel != null) {
            setUpSourcesList();
        }
    }
}
