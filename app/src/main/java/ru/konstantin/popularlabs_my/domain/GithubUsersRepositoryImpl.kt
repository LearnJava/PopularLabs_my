package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.RetrofitService

class GithubUsersRepositoryImpl(
    private val retrofitService: RetrofitService,
): GithubUsersRepository {

    override fun getUsers(): Single<List<GithubUserModel>> {
        return retrofitService.getUsers()
    }
}