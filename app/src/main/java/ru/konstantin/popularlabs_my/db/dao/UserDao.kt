package ru.konstantin.popularlabs_my.db.dao

import androidx.room.*
import ru.konstantin.popularlabs_my.db.model.RoomGithubUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubUser)

    @Update
    fun update(user: RoomGithubUser)

    @Delete
    fun delete(user: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubUser>)

    @Query("SELECT * FROM RoomGithubUser")
    fun getAll(): List<RoomGithubUser>

    @Query("SELECT * FROM RoomGithubUser WHERE login = :login LIMIT 1")
    fun getByLogin(login: String): RoomGithubUser?
}