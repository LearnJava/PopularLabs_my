package ru.konstantin.popularlabs_my.domain.cache

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubRepoCacheRepository {
    fun getCacheRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>>
}