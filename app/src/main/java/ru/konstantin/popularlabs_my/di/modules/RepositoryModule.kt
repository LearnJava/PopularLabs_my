package ru.konstantin.popularlabs_my.di.modules

import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.domain.*
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCache
import ru.konstantin.popularlabs_my.domain.cache.GithubRepoCacheImpl
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCache
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCacheImpl
import ru.konstantin.popularlabs_my.domain.retrofit.GithubRepoRetrofit
import ru.konstantin.popularlabs_my.domain.retrofit.GithubRepoRetrofitImpl
import ru.konstantin.popularlabs_my.domain.retrofit.GithubUsersRetrofit
import ru.konstantin.popularlabs_my.domain.retrofit.GithubUsersRetrofitImpl
import ru.konstantin.popularlabs_my.remote.RetrofitService
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun usersRepo(
        networkStatus: NetworkStatus,
        githubUsersRetrofit: GithubUsersRetrofit,
        githubUsersCache: GithubUsersCache
    ): GithubUsersRepository {
        return GithubUsersRepositoryImpl(networkStatus, githubUsersRetrofit, githubUsersCache)
    }

    @Provides
    @Singleton
    fun usersRetrofit(
        retrofitService: RetrofitService,
        db: AppDatabase
    ): GithubUsersRetrofit {
        return GithubUsersRetrofitImpl(retrofitService, db)
    }

    @Provides
    @Singleton
    fun usersCache(
        db: AppDatabase
    ): GithubUsersCache {
        return GithubUsersCacheImpl(db)
    }

    @Provides
    @Singleton
    fun reposRepo(
        networkStatus: NetworkStatus,
        githubRepoRetrofit: GithubRepoRetrofit,
        githubRepoCache: GithubRepoCache
    ): GithubRepoRepository {
        return GithubRepoRepositoryImpl(networkStatus, githubRepoRetrofit, githubRepoCache)
    }

    @Provides
    @Singleton
    fun reposRetrofit(
        retrofitService: RetrofitService,
        db: AppDatabase
    ): GithubRepoRetrofit {
        return GithubRepoRetrofitImpl(retrofitService, db)
    }

    @Provides
    @Singleton
    fun reposCache(
        db: AppDatabase
    ): GithubRepoCache {
        return GithubRepoCacheImpl(db)
    }

    @Provides
    @Singleton
    fun userChoose(): UserChooseRepository {
        return UserChooseRepositoryImpl()
    }
}