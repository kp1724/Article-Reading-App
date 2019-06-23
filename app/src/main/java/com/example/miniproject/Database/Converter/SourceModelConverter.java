package com.example.miniproject.Database.Converter;

import android.arch.persistence.room.TypeConverter;

import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.Repository.SourceRepository;

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
