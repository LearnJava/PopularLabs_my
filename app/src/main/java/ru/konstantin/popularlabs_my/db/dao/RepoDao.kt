package ru.konstantin.popularlabs_my.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.konstantin.popularlabs_my.db.model.RoomGithubRepo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubRepo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubRepo>)

    @Query("SELECT * FROM RoomGithubRepo")
    fun getAll(): List<RoomGithubRepo>

    @Query("SELECT * FROM RoomGithubRepo WHERE userId = :userId")
    fun getByUserId(userId: String): List<RoomGithubRepo>
}