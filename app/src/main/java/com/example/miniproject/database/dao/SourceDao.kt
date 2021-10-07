package com.example.miniproject.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniproject.model.SourceModel

@Dao
interface SourceDao {
    @Query("SELECT * FROM tblSource")
    fun loadAllSources(): LiveData<List<SourceModel>?>?

    @Query("SELECT * FROM tblSource WHERE id = :id")
    fun getSourceById(id: String?): LiveData<SourceModel?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSources(sourceModels: List<SourceModel?>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSource(SourceModel: SourceModel?)

    @Delete
    fun deleteSource(sourceModel: SourceModel?)

    @Query("DELETE FROM tblSource WHERE createdDateTime < :time")
    fun deleteAllSources(time: Long)
}