package ru.konstantin.popularlabs_my.domain.cache

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubRepoOwner
import ru.konstantin.popularlabs_my.model.GithubUserModel

class GithubRepoCacheImpl (
    private val db: AppDatabase
): GithubRepoCache {
    override fun getCacheRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return Single.fromCallable {
            db.repositoryDao.getByUserId(userModel.id).map {
                GithubRepoModel(it.id, it.name, GithubRepoOwner(it.userId), it.forksCount)
            }
        }
    }
}