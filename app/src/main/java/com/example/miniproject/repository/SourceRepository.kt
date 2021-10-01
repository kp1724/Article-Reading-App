package com.example.miniproject.repository

import android.content.Context
import com.example.miniproject.database.controller.DatabaseController.Companion.getInstance
import com.example.miniproject.model.AbstractResponse
import com.example.miniproject.retrofitService.RetrofitController
import com.example.miniproject.retrofitService.ServiceClient
import com.example.miniproject.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SourceRepository(private val context: Context) {

    fun getSourceListFromDb() = getInstance(context)!!.sourceDao()!!.loadAllSources()

    val sourceListFromWebAndInsertInDB: Unit
        get() {
            val serviceClient = RetrofitController.instance?.create(
                ServiceClient::class.java
            )
            serviceClient?.sourcesList?.enqueue(object : Callback<AbstractResponse?> {
                override fun onResponse(
                    call: Call<AbstractResponse?>,
                    response: Response<AbstractResponse?>
                ) {
                    if (response.body() != null && (response.body()!!.status == "ok")) AppExecutors.instance?.diskIO?.execute {
                        val date = Calendar.getInstance()
                        date.add(Calendar.HOUR_OF_DAY, -1)
                        getInstance(context)!!.sourceDao()!!
                            .deleteAllSources(date.time.time)
                        getInstance(context)!!.sourceDao()!!
                            .insertAllSources(response.body()!!.sources)
                    }
                }

                override fun onFailure(call: Call<AbstractResponse?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

    companion object {
        private val TAG = "SourceRepository"
    }
}