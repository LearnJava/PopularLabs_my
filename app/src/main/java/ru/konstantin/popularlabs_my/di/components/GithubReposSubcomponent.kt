package ru.konstantin.popularlabs_my.di.components

import dagger.Subcomponent
import ru.konstantin.popularlabs_my.di.modules.GithubReposModule
import ru.konstantin.popularlabs_my.di.scope.ReposScope
import ru.konstantin.popularlabs_my.ui.repos.ReposPresenter


@ReposScope
@Subcomponent(
    modules = [
        GithubReposModule::class
    ]
)

interface GithubReposSubcomponent {

    fun provideReposPresenter(): ReposPresenter
}