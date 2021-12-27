package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubRepoRepository {
    fun getRepos(reposUrl: GithubUserModel): Single<List<GithubRepoModel>>
}