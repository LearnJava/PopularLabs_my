package ru.konstantin.popularlabs_my.domain.cache

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.model.GithubUserModel

class GithubUsersCacheImpl (
    private val db: AppDatabase
): GithubUsersCache {
    override fun getCacheUsers(): Single<List<GithubUserModel>> {
        return Single.fromCallable {
            db.userDao.getAll().map { roomModel ->
                GithubUserModel(
                    roomModel.id, roomModel.login, roomModel.avatarUrl, roomModel.reposUrl
                )
            }
        }
    }
}