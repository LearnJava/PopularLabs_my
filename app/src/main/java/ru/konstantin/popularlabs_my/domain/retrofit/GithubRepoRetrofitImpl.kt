package ru.konstantin.popularlabs_my.domain.retrofit

import io.reactivex.rxjava3.core.Single
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.db.model.RoomGithubRepo
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.RetrofitService

class GithubRepoRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
) : GithubRepoRetrofit {
    override fun getRetrofitRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos(userModel.reposUrl)
            .flatMap { repos ->
                val dbRepos = repos.map {
                    RoomGithubRepo(it.id, it.name, it.owner.id, it.forksCount)
                }
                db.repositoryDao.insert(dbRepos)
                    .toSingle { repos }
            }
    }
}