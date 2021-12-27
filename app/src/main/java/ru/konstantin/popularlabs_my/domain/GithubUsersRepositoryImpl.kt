package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCacheRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.RetrofitService

class GithubUsersRepositoryImpl(
    private val roomGithubUsersCache: GithubUsersCacheRepository,
): GithubUsersRepository {
    override fun getCachedUsers(): Single<List<GithubUserModel>> {
        return roomGithubUsersCache.getCacheUsers()
    }
}