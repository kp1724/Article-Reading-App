package com.example.miniproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.miniproject.model.SourceModel
import com.example.miniproject.repository.SourceRepository

class SourceViewModel(application: Application) : AndroidViewModel(application) {
    private val sourceRepository: SourceRepository = SourceRepository(application)
    fun callApiAndSaveInDB() {
        sourceRepository.sourceListFromWebAndInsertInDB
    }

    fun getSourceList(): LiveData<List<SourceModel?>?>? {
        return sourceRepository.getSourceListFromDb()
    }
}