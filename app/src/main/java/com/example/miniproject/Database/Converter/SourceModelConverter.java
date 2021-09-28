package com.example.miniproject.Database.Converter;

import androidx.room.TypeConverter;

import com.example.miniproject.Model.SourceModel;

public class SourceModelConverter {
    @TypeConverter
    public static SourceModel toSource(String  id) {
        return id == null ? null : new SourceModel(id);
    }

    @TypeConverter
    public static String toSourceId(SourceModel sourceModel) {
        return sourceModel == null ? null : sourceModel.getId();
    }
}
