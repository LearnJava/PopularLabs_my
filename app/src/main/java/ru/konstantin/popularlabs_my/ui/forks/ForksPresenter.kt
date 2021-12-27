package ru.konstantin.popularlabs_my.ui.forks

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.ui.main.MainView
import javax.inject.Inject

class ForksPresenter @Inject constructor(
    private val router: Router
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}