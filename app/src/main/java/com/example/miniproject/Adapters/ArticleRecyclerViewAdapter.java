package com.example.miniproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.R;
import com.example.miniproject.Utils.AppExecutors;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {
    private List<ArticleModel> articleModels;
    private Context mContext;

    public ArticleRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.article_recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final ArticleModel model = articleModels.get(i);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.publishedAt.setText(model.getPublishedAt());
        holder.author.setText(model.getAuthor());
        Picasso.get().load(model.getUrlToImage()).into(holder.articleImage);
        holder.articleParent.setTag(model);
        holder.articleParent.setOnClickListener((View.OnClickListener) mContext);
    }

    @Override
    public int getItemCount() {
        return articleModels == null ? 0 : articleModels.size();
    }

    public void setArticlesList(List<ArticleModel> articleModels) {
        this.articleModels = articleModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout articleParent;
        private TextView title, description, publishedAt,author;
        private ImageView articleImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleParent = itemView.findViewById(R.id.article_cardview_parent);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            publishedAt = itemView.findViewById(R.id.published_at);
            author = itemView.findViewById(R.id.author);
            articleImage = itemView.findViewById(R.id.article_image);
        }
    }
}
