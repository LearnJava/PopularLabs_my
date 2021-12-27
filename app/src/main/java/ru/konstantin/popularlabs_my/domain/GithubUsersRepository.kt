package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubUsersRepository {
    fun getUsers(): Single<List<GithubUserModel>>
}