package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCacheRepository
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

class GithubRepoRepositoryImpl(
    private val roomGithubRepositoriesCache: GithubRepoCacheRepository
): GithubRepoRepository {

    override fun getRepos(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return roomGithubRepositoriesCache.getCacheRepo(userModel)
    }
}