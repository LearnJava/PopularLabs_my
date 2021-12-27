package ru.konstantin.popularlabs_my.di.components

import dagger.Subcomponent
import ru.konstantin.popularlabs_my.di.modules.GithubForksModule
import ru.konstantin.popularlabs_my.di.scope.ForksScope
import ru.konstantin.popularlabs_my.ui.forks.ForksPresenter

@ForksScope
@Subcomponent(
    modules = [
        GithubForksModule::class
    ]
)

interface GithubForksSubcomponent {

    fun provideForksPresenter(): ForksPresenter
}