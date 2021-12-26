package ru.konstantin.popularlabs_my.ui.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.screens.AppScreens

class MainPresenter(
    private val router: Router
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.replaceScreen(AppScreens.usersScreen())
    }

    fun backPressed() {
        router.exit()
    }
}