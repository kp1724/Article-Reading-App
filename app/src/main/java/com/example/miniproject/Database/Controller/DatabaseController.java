package com.example.miniproject.Database.Controller;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.miniproject.Database.Converter.DateConverter;
import com.example.miniproject.Database.Converter.SourceModelConverter;
import com.example.miniproject.Database.Dao.ArticleDao;
import com.example.miniproject.Database.Dao.SourceDao;
import com.example.miniproject.Model.ArticleModel;
import com.example.miniproject.Model.SourceModel;

@Database(entities = {SourceModel.class, ArticleModel.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, SourceModelConverter.class})
public abstract class DatabaseController extends RoomDatabase {

    private static final String LOG_TAG = DatabaseController.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "ProjectDataBase";
    private static DatabaseController sInstance;

    public static DatabaseController getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DatabaseController.class, DatabaseController.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract SourceDao sourceDao();

    public abstract ArticleDao articleDao();
}
