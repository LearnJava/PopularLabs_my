package ru.konstantin.popularlabs_my.domain.cache

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.db.model.RoomGithubRepo
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubRepoOwner
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.ApiHolder
import ru.konstantin.popularlabs_my.remote.RetrofitService
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

class RoomGithubRepositoriesCache(
    private val networkStatus: NetworkStatus
): GithubRepoCacheRepository {
    private val retrofitService: RetrofitService = ApiHolder.retrofitService
    private val db: AppDatabase = AppDatabase.instance

    override fun getCacheRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return if (networkStatus.isOnline()) {
            retrofitService.getRepos(userModel.reposUrl)
                .flatMap { repos ->
                    Single.fromCallable {
                        val dbRepos = repos.map {
                            RoomGithubRepo(it.id, it.name, it.owner.id, it.forksCount)
                        }
                        db.repositoryDao.insert(dbRepos)
                        repos
                    }
                }
        } else {
            Single.fromCallable {
                db.repositoryDao.getByUserId(userModel.id)
                    .map { GithubRepoModel(
                        it.id, it.name, GithubRepoOwner(it.userId), it.forksCount) }
            }
        }
    }
}