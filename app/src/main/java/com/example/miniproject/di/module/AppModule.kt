package com.example.miniproject.di.module

import android.content.Context
import com.example.miniproject.database.controller.DatabaseController
import com.example.miniproject.retrofitService.RetrofitController
import com.example.miniproject.retrofitService.ServiceClient
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideDataBaseController(context: Context): DatabaseController {
        return DatabaseController.getInstance(context)
    }

    @Provides
    fun provideServiceClient(): ServiceClient {
        return RetrofitController.instance.create(ServiceClient::class.java)
    }


}