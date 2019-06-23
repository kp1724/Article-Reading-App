package com.example.miniproject.Model;

import java.util.ArrayList;

public class AbstractResponse {
    private String status;
    private int totalResults;
    private ArrayList<SourceModel> sources;
    private ArrayList<ArticleModel> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<SourceModel> getSources() {
        return sources;
    }

    public void setSources(ArrayList<SourceModel> sources) {
        this.sources = sources;
    }

    public ArrayList<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticleModel> articles) {
        this.articles = articles;
    }
}
