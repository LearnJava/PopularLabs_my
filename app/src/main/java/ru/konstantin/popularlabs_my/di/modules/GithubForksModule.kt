package ru.konstantin.popularlabs_my.di.modules

import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.di.scope.ForksScope
import ru.konstantin.popularlabs_my.di.scope.containers.ForksScopeContainer

@Module
abstract class GithubForksModule {

    companion object {
        @ForksScope
        @Provides
        fun forksScopeContainer(app: App): ForksScopeContainer = app
    }
}
