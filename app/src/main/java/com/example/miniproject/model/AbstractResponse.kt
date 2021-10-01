package com.example.miniproject.model

import java.util.*

class AbstractResponse {
    var status: String? = null
    var totalResults = 0
    var sources: ArrayList<SourceModel>? = null
    var articles: ArrayList<ArticleModel>? = null
}