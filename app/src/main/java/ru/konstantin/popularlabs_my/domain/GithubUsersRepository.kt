package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubUsersRepository {
    fun getCachedUsers(): Single<List<GithubUserModel>>

//    fun getRemoteUsers(): Single<List<GithubUserModel>>
}