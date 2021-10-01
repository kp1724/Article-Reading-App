package com.example.miniproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tblArticle")
class ArticleModel(var author: String, @field:PrimaryKey var title: String, var description: String,
                   var url: String, @field:ColumnInfo(name = "url_to_image") var urlToImage: String, @field:ColumnInfo(name = "published_at") var publishedAt: String, var content: String) : Serializable {
    var source: SourceModel? = null
}