package ru.konstantin.popularlabs_my.di.modules

import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.di.scope.ReposScope
import ru.konstantin.popularlabs_my.di.scope.containers.ReposScopeContainer
import ru.konstantin.popularlabs_my.domain.GithubRepoRepository
import ru.konstantin.popularlabs_my.domain.GithubRepoRepositoryImpl
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCache
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCacheImpl
import ru.konstantin.popularlabs_my.domain.retrofit.GithubRepoRetrofit
import ru.konstantin.popularlabs_my.domain.retrofit.GithubRepoRetrofitImpl
import ru.konstantin.popularlabs_my.remote.RetrofitService
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

@Module
abstract class GithubReposModule {
    companion object {
        @ReposScope
        @Provides
        fun reposRepo(
            networkStatus: NetworkStatus,
            githubRepoRetrofit: GithubRepoRetrofit,
            githubRepoCache: GithubRepoCache
        ): GithubRepoRepository {
            return GithubRepoRepositoryImpl(networkStatus, githubRepoRetrofit, githubRepoCache)
        }

        @ReposScope
        @Provides
        fun reposRetrofit(
            retrofitService: RetrofitService,
            db: AppDatabase
        ): GithubRepoRetrofit {
            return GithubRepoRetrofitImpl(retrofitService, db)
        }

        @ReposScope
        @Provides
        fun reposCache(
            db: AppDatabase
        ): GithubRepoCache {
            return GithubRepoCacheImpl(db)
        }

        @ReposScope
        @Provides
        fun scopeContainer(app: App): ReposScopeContainer = app
    }
}