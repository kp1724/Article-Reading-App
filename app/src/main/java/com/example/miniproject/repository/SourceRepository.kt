package com.example.miniproject.repository

import com.example.miniproject.database.controller.DatabaseController
import com.example.miniproject.model.AbstractResponse
import com.example.miniproject.retrofitService.ServiceClient
import com.example.miniproject.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class SourceRepository @Inject constructor(
    private val databaseController: DatabaseController,
    private val serviceClient: ServiceClient
) {

    fun getSourceListFromDb() = databaseController.sourceDao().loadAllSources()

    val sourceListFromWebAndInsertInDB: Unit
        get() {
            serviceClient.sourcesList?.enqueue(object : Callback<AbstractResponse?> {
                override fun onResponse(
                    call: Call<AbstractResponse?>,
                    response: Response<AbstractResponse?>
                ) {
                    if (response.body() != null && (response.body()?.status == "ok")) {
                        AppExecutors.instance?.diskIO?.execute {
                            val date = Calendar.getInstance()
                            date.add(Calendar.HOUR_OF_DAY, -1)
                            databaseController.sourceDao()
                                .deleteAllSources(date.time.time)
                            databaseController.sourceDao()
                                .insertAllSources(response.body()?.sources)
                        }
                    }
                }

                override fun onFailure(call: Call<AbstractResponse?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

}