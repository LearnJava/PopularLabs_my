package ru.konstantin.popularlabs_my.ui.forks

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.ui.main.MainView

class ForksPresenter(
    private val router: Router,
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}