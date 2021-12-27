package ru.konstantin.popularlabs_my.di.components

import dagger.Component
import ru.konstantin.popularlabs_my.di.modules.*
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.ui.forks.ForksPresenter
import ru.konstantin.popularlabs_my.ui.main.MainActivity
import ru.konstantin.popularlabs_my.ui.main.MainPresenter
import ru.konstantin.popularlabs_my.ui.repos.ReposPresenter
import ru.konstantin.popularlabs_my.ui.users.UsersPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        ContextModule::class,
        NetworkModule::class,
        CacheModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {

    fun mainPresenter(): MainPresenter
    fun forksPresenter(): ForksPresenter
    fun usersPresenter(): UsersPresenter
    fun reposPresenter(): ReposPresenter

    fun injectMainActivity(mainActivity: MainActivity)

    fun userChoose(): UserChooseRepository
}