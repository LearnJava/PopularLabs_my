package ru.konstantin.popularlabs_my.di.components

import dagger.Component
import ru.konstantin.popularlabs_my.di.modules.*
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.ui.main.MainActivity
import ru.konstantin.popularlabs_my.ui.main.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class,
        DbModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {

    /** AppComponent */ //region
    fun mainPresenter(): MainPresenter
    fun injectMainActivity(mainActivity: MainActivity)
    fun userChoose(): UserChooseRepository
    //endregion

    /** Subcomponents */ //region
    fun usersSubcomponent(): GithubUsersSubcomponent
    fun reposSubcomponent(): GithubReposSubcomponent
    fun forksSubcomponent(): GithubForksSubcomponent
    //endregion
}