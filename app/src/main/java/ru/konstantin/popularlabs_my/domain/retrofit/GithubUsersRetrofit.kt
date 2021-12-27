package ru.konstantin.popularlabs_my.domain.retrofit

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface GithubUsersRetrofit {
    fun getRetrofitUsers(): Single<List<GithubUserModel>>
}