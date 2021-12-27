package ru.konstantin.popularlabs_my.di.components

import dagger.Subcomponent
import ru.konstantin.popularlabs_my.di.modules.GithubUsersModule
import ru.konstantin.popularlabs_my.di.scope.UsersScope
import ru.konstantin.popularlabs_my.ui.users.UsersPresenter

@UsersScope
@Subcomponent(
    modules = [
        GithubUsersModule::class
    ]
)
interface GithubUsersSubcomponent {

    fun provideUsersPresenter(): UsersPresenter
}