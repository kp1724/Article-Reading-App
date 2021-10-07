package com.example.miniproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R
import com.example.miniproject.model.SourceModel

class SourceRecyclerViewAdapter(private val mContext: Context) :
    RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder>() {
    private var mSources: List<SourceModel>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.source_recyclerview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        mSources?.get(i)?.let { model ->
            holder.sourceName.text = model.name
            holder.sourceDescription.text = model.description
            holder.category.text = model.category
            holder.language.text = model.language
            holder.country.text = model.country
            holder.parentCard.tag = model
            holder.parentCard.setOnClickListener(mContext as View.OnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return mSources?.size?:0
    }

    fun setSourcesList(sourceModels: List<SourceModel>?) {
        mSources = sourceModels
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentCard: ConstraintLayout = itemView.findViewById(R.id.sources_cardview_parent)
        val sourceName: TextView = itemView.findViewById(R.id.source_name)
        val sourceDescription: TextView = itemView.findViewById(R.id.description)
        val language: TextView = itemView.findViewById(R.id.language)
        val country: TextView = itemView.findViewById(R.id.country)
        val category: TextView = itemView.findViewById(R.id.category)

    }
}