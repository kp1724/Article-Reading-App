package com.example.miniproject.database.converter

import androidx.room.TypeConverter
import com.example.miniproject.model.SourceModel

object SourceModelConverter {
    @JvmStatic
    @TypeConverter
    fun toSource(id: String?): SourceModel? {
        return id?.let { SourceModel(it) }
    }

    @JvmStatic
    @TypeConverter
    fun toSourceId(sourceModel: SourceModel?): String? {
        return sourceModel?.id
    }
}