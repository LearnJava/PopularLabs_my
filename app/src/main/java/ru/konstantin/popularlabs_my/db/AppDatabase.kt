package ru.konstantin.popularlabs_my.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.db.dao.RepoDao
import ru.konstantin.popularlabs_my.db.dao.UserDao
import ru.konstantin.popularlabs_my.db.model.RoomGithubRepo
import ru.konstantin.popularlabs_my.db.model.RoomGithubUser

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepo::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val repositoryDao: RepoDao

    companion object {
        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .build()
        }
    }
}