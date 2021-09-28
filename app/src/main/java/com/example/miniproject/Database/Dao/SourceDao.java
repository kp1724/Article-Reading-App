package com.example.miniproject.Database.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.miniproject.Model.SourceModel;

import java.util.List;

@Dao
public interface SourceDao {
    @Query("SELECT * FROM tblSource")
    LiveData<List<SourceModel>> loadAllSources();

    @Query("SELECT * FROM tblSource WHERE id = :id")
    LiveData<SourceModel> getSourceById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSources(List<SourceModel> sourceModels);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSource(SourceModel SourceModel);

    @Delete
    void deleteSource(SourceModel sourceModel);

    @Query("DELETE FROM tblSource WHERE createdDateTime < :time")
    void deleteAllSources(long time);
}
