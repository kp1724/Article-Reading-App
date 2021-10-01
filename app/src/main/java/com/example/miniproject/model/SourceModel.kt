package com.example.miniproject.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "tblSource")
class SourceModel : Serializable {
    @PrimaryKey
    var id: String
    var name: String? = null
    var description: String? = null
    var url: String? = null
    var category: String? = null
    var language: String? = null
    var country: String? = null
    var createdDateTime: Date? = null

    constructor(id: String, name: String?, description: String?, url: String?, category: String?, language: String?, country: String?) {
        this.id = id
        this.name = name
        this.description = description
        this.url = url
        this.category = category
        this.language = language
        this.country = country
        createdDateTime = Calendar.getInstance().time
    }

    @Ignore
    constructor(id: String) {
        this.id = id
    }
}