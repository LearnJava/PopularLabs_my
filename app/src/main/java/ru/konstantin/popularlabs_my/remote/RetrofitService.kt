package ru.konstantin.popularlabs_my.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface RetrofitService {

    @GET("/users")
    fun getUsers(): Single<List<GithubUserModel>>

    @GET
    fun getRepos(@Url url: String): Single<List<GithubRepoModel>>
}