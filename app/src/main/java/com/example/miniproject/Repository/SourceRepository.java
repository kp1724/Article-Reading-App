package com.example.miniproject.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.miniproject.Database.Controller.DatabaseController;
import com.example.miniproject.Model.AbstractResponse;
import com.example.miniproject.Model.SourceModel;
import com.example.miniproject.RetrofitService.RetrofitController;
import com.example.miniproject.RetrofitService.ServiceClient;
import com.example.miniproject.Utils.AppExecutors;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceRepository {

    private static final String TAG = "SourceRepository";
    private final Context context;

    public SourceRepository(Context context) {
        this.context = context;
    }

    public LiveData<List<SourceModel>> getSourceListFromDb() {
        LiveData<List<SourceModel>> data;
        data = DatabaseController.getInstance(context).sourceDao().loadAllSources();
        return data;
    }

    public void getSourceListFromWebAndInsertInDB() {
        ServiceClient serviceClient = RetrofitController.getInstance().create(ServiceClient.class);
        serviceClient.getSourcesList().enqueue(new Callback<AbstractResponse>() {
            @Override
            public void onResponse(Call<AbstractResponse> call, final Response<AbstractResponse> response) {
                if (response.body() != null && response.body().getStatus().equals("ok"))
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Calendar date = Calendar.getInstance();
                            date.add(Calendar.HOUR_OF_DAY,-1);
                            DatabaseController.getInstance(context).sourceDao().deleteAllSources(date.getTime().getTime());
                            DatabaseController.getInstance(context).sourceDao().insertAllSources(response.body().getSources());
                        }
                    });
            }

            @Override
            public void onFailure(Call<AbstractResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

}
