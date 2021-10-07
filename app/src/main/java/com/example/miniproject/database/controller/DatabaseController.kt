package com.example.miniproject.database.controller

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.miniproject.database.converter.DateConverter
import com.example.miniproject.database.converter.SourceModelConverter
import com.example.miniproject.database.dao.ArticleDao
import com.example.miniproject.database.dao.SourceDao
import com.example.miniproject.extension.KotlinExtension.BooleanExtension.isFalse
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.model.SourceModel

@Database(entities = [SourceModel::class, ArticleModel::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, SourceModelConverter::class)
abstract class DatabaseController : RoomDatabase() {
    abstract fun sourceDao(): SourceDao
    abstract fun articleDao(): ArticleDao

    companion object {
        private val LOG_TAG = DatabaseController::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "ProjectDataBase"
        private lateinit var sInstance: DatabaseController
        @JvmStatic
        fun getInstance(context: Context): DatabaseController {
            if (this::sInstance.isInitialized.isFalse()) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(context.applicationContext,
                            DatabaseController::class.java, DATABASE_NAME)
                            .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance
        }
    }
}