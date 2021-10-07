package com.example.miniproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.model.SourceModel;
import com.example.miniproject.R;

import java.util.List;

public class SourceRecyclerViewAdapter extends RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder> {
    private List<SourceModel> mSources;
    private Context mContext;

    public SourceRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.source_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        SourceModel model = mSources.get(i);
        holder.sourceName.setText(model.getName());
        holder.sourceDescription.setText(model.getDescription());
        holder.category.setText(model.getCategory());
        holder.language.setText(model.getLanguage());
        holder.country.setText(model.getCountry());
        holder.parentCard.setTag(model);
        holder.parentCard.setOnClickListener((View.OnClickListener) mContext);
    }

    @Override
    public int getItemCount() {
        return mSources == null ? 0 : mSources.size();
    }

    public void setSourcesList(List<SourceModel> sourceModels) {
        mSources = sourceModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout parentCard;
        private TextView sourceName, sourceDescription,
                language, country, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCard = itemView.findViewById(R.id.sources_cardview_parent);
            sourceName = itemView.findViewById(R.id.source_name);
            sourceDescription = itemView.findViewById(R.id.description);
            language = itemView.findViewById(R.id.language);
            country = itemView.findViewById(R.id.country);
            category = itemView.findViewById(R.id.category);
        }
    }
}
