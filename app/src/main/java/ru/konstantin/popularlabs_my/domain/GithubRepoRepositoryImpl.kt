package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCache
import ru.konstantin.popularlabs_my.domain.retrofit.GithubRepoRetrofit
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

class GithubRepoRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubRepoRetrofit: GithubRepoRetrofit,
    private val githubRepoCache: GithubRepoCache
) : GithubRepoRepository {
    override fun getRepos(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return if (networkStatus.isOnline())
            githubRepoRetrofit.getRetrofitRepo(userModel)
        else
            githubRepoCache.getCacheRepo(userModel)
    }
}