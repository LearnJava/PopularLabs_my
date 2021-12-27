package ru.konstantin.popularlabs_my.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.model.RoomGithubRepo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubRepo): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubRepo>): Completable

    @Query("SELECT * FROM RoomGithubRepo")
    fun getAll(): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE userId = :userId")
    fun getByUserId(userId: String): Single<List<RoomGithubRepo>>
}