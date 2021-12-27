package ru.konstantin.popularlabs_my.di.scope.containers

import ru.konstantin.popularlabs_my.di.components.GithubReposSubcomponent

interface ReposScopeContainer {

    fun initGithubReposSubcomponent(): GithubReposSubcomponent

    fun destroyGithubReposSubcomponent()
}