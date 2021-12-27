package ru.konstantin.popularlabs_my.di.scope.containers

import ru.konstantin.popularlabs_my.di.components.GithubUsersSubcomponent

interface UsersScopeContainer {

    fun initGithubUsersSubcomponent(): GithubUsersSubcomponent

    fun destroyGithubUsersSubcomponent()
}