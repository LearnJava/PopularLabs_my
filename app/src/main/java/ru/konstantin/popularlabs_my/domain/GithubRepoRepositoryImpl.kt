package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.RetrofitService

class GithubRepoRepositoryImpl(
    private val retrofitService: RetrofitService
): GithubRepoRepository {
    override fun getRepos(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos(userModel.reposUrl)
    }
}