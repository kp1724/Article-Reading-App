package com.example.miniproject.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.miniproject.Database.Controller.DatabaseController;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.Repository.SourceRepository;

import java.util.List;

public class SourceViewModel extends AndroidViewModel {
    private SourceRepository sourceRepository;

    public SourceViewModel(@NonNull Application application) {
        super(application);
        this.sourceRepository = new SourceRepository(application);
    }

    public void callApiAndSaveInDB(){
        sourceRepository.getSourceListFromWebAndInsertInDB();
    }

    public LiveData<List<SourceModel>> getSourceList() {
        return sourceRepository.getSourceListFromDb();
    }
}
