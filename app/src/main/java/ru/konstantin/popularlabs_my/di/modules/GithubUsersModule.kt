package ru.konstantin.popularlabs_my.di.modules

import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.db.AppDatabase
import ru.konstantin.popularlabs_my.di.scope.UsersScope
import ru.konstantin.popularlabs_my.di.scope.containers.UsersScopeContainer
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.domain.GithubUsersRepositoryImpl
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCache
import ru.konstantin.popularlabs_my.domain.cache.GithubUsersCacheImpl
import ru.konstantin.popularlabs_my.domain.retrofit.GithubUsersRetrofit
import ru.konstantin.popularlabs_my.domain.retrofit.GithubUsersRetrofitImpl
import ru.konstantin.popularlabs_my.remote.RetrofitService
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus

@Module
abstract class GithubUsersModule {

    companion object {
        @UsersScope
        @Provides
        fun usersRepo(
            networkStatus: NetworkStatus,
            githubUsersRetrofit: GithubUsersRetrofit,
            githubUsersCache: GithubUsersCache
        ): GithubUsersRepository {
            return GithubUsersRepositoryImpl(networkStatus, githubUsersRetrofit, githubUsersCache)
        }

        @UsersScope
        @Provides
        fun usersRetrofit(
            retrofitService: RetrofitService,
            db: AppDatabase
        ): GithubUsersRetrofit {
            return GithubUsersRetrofitImpl(retrofitService, db)
        }

        @UsersScope
        @Provides
        fun usersCache(
            db: AppDatabase
        ): GithubUsersCache {
            return GithubUsersCacheImpl(db)
        }

        @UsersScope
        @Provides
        fun scopeContainer(app: App): UsersScopeContainer = app
    }
}
