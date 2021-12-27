package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCache
import ru.konstantin.popularlabs_my.domain.retrofit.GithubUsersRetrofit
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

class GithubUsersRepositoryImpl (
    private val networkStatus: NetworkStatus,
    private val githubUsersRetrofit: GithubUsersRetrofit,
    private val githubUsersCache: GithubUsersCache
): GithubUsersRepository {
    override fun getUsers(): Single<List<GithubUserModel>> {
        return if (networkStatus.isOnline())
            githubUsersRetrofit.getRetrofitUsers()
        else
            githubUsersCache.getCacheUsers()
    }
}