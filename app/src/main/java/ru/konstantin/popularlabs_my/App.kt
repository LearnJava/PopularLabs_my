package ru.konstantin.popularlabs_my

import android.app.Application
import ru.konstantin.popularlabs_my.di.components.*
import ru.konstantin.popularlabs_my.di.modules.AppModule
import ru.konstantin.popularlabs_my.di.scope.containers.ForksScopeContainer
import ru.konstantin.popularlabs_my.di.scope.containers.ReposScopeContainer
import ru.konstantin.popularlabs_my.di.scope.containers.UsersScopeContainer

class App : Application(), UsersScopeContainer, ReposScopeContainer, ForksScopeContainer {
    /** Исходные данные */ //region
    // appComponent
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    // usersSubcomponent
    var usersSubcomponent: GithubUsersSubcomponent? = null

    // reposSubcomponent
    var reposSubcomponent: GithubReposSubcomponent? = null

    // forksSubcomponent
    var forksSubcomponent: GithubForksSubcomponent? = null
    //endregion

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!
    }

    /** Методы UsersScopeContainer */ //region
    override fun initGithubUsersSubcomponent() = appComponent.usersSubcomponent().also {
        usersSubcomponent = it
    }

    override fun destroyGithubUsersSubcomponent() {
        usersSubcomponent = null
    }
    //endregion

    /** Методы ReposScopeContainer */ //region
    override fun initGithubReposSubcomponent() = appComponent.reposSubcomponent().also {
        reposSubcomponent = it
    }

    override fun destroyGithubReposSubcomponent() {
        reposSubcomponent = null
    }
    //endregion

    /** Методы ForksScopeContainer */ //region
    override fun initForksSubcomponent() = appComponent.forksSubcomponent().also {
        forksSubcomponent = it
    }

    override fun destroyForksSubcomponent() {
        forksSubcomponent = null
    }
    //endregion
}