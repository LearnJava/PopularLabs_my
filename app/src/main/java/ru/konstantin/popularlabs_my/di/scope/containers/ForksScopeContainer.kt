package ru.konstantin.popularlabs_my.di.scope.containers

import ru.konstantin.popularlabs_my.di.components.GithubForksSubcomponent

interface ForksScopeContainer {

    fun initForksSubcomponent(): GithubForksSubcomponent

    fun destroyForksSubcomponent()
}