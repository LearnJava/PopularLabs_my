package ru.konstantin.popularlabs_my.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.db.AppDatabase
import javax.inject.Singleton

private const val DB_NAME = "database.db"

@Module
class DbModule {
    @Singleton
    @Provides
    fun db(context: Context): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .build()
}