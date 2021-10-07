package com.example.miniproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.miniproject.model.SourceModel
import com.example.miniproject.repository.SourceRepository
import javax.inject.Inject

class SourceViewModel @Inject constructor(private var sourceRepository: SourceRepository) : ViewModel() {

    fun callApiAndSaveInDB() {
        sourceRepository.sourceListFromWebAndInsertInDB
    }

    fun getSourceList(): LiveData<List<SourceModel>?>? {
        return sourceRepository.getSourceListFromDb()
    }
}