package ru.konstantin.popularlabs_my.domain.retrofit

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.db.model.RoomGithubUser
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.RetrofitService

class GithubUsersRetrofitImpl (
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubUsersRetrofit {
    override fun getRetrofitUsers(): Single<List<GithubUserModel>> {
        return retrofitService.getUsers()
            .flatMap { users ->
                Single.fromCallable {
                    val roomUsers = users.map { user ->
                        RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                    }
                    db.userDao.insert(roomUsers)
                    users
                }
            }
    }
}