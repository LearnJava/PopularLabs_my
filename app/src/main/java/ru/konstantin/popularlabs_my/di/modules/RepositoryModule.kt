package ru.konstantin.popularlabs_my.di.modules

import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.domain.UserChooseRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun userChoose(): UserChooseRepository {
        return UserChooseRepositoryImpl()
    }
}