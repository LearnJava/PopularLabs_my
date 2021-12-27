package ru.konstantin.popularlabs_my.domain.cache

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.db.model.RoomGithubUser
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.ApiHolder
import ru.konstantin.popularlabs_my.remote.RetrofitService
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

class RoomGithubUsersCache(
    private val networkStatus: NetworkStatus,
): GithubUsersCacheRepository {
    private val retrofitService: RetrofitService = ApiHolder.retrofitService
    private val db: AppDatabase = AppDatabase.instance

    override fun getCacheUsers(): Single<List<GithubUserModel>> {
        return if (networkStatus.isOnline()) {
            retrofitService.getUsers()
                .flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                        }
                        db.userDao.insert(roomUsers)
                        users
                    }
                }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map { roomModel ->
                    GithubUserModel(
                        roomModel.id, roomModel.login, roomModel.avatarUrl, roomModel.reposUrl)
                }
            }
        }
    }
}