package ru.konstantin.popularlabs_my.domain.retrofit

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubRepoRetrofit {
    fun getRetrofitRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>>
}