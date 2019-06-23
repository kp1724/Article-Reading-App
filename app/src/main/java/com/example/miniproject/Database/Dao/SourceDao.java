package com.example.miniproject.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
