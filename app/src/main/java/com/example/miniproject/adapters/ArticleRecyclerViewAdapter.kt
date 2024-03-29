package com.example.miniproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R
import com.example.miniproject.model.ArticleModel
import com.squareup.picasso.Picasso

class ArticleRecyclerViewAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {
    private var articleModels: List<ArticleModel>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.article_recyclerview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        articleModels?.getOrNull(i)?.let { model ->
            holder.title.text = model.title
            holder.description.text = model.description
            holder.publishedAt.text = model.publishedAt
            holder.author.text = model.author
            Picasso.get().load(model.urlToImage).into(holder.articleImage)
            holder.articleParent.tag = model
            holder.articleParent.setOnClickListener(mContext as View.OnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return articleModels?.size ?: 0
    }

    fun setArticlesList(articleModels: List<ArticleModel>?) {
        this.articleModels = articleModels
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleParent: RelativeLayout = itemView.findViewById(R.id.article_cardview_parent)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val publishedAt: TextView = itemView.findViewById(R.id.published_at)
        val author: TextView = itemView.findViewById(R.id.author)
        val articleImage: ImageView = itemView.findViewById(R.id.article_image)

    }
}