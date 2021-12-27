package ru.konstantin.popularlabs_my.ui.forks

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.di.scope.containers.ForksScopeContainer
import ru.konstantin.popularlabs_my.ui.main.MainView
import javax.inject.Inject

class ForksPresenter @Inject constructor(
    private val router: Router,
    private val forksScopeContainer: ForksScopeContainer
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    /** Уничтожение ForksSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        forksScopeContainer.destroyForksSubcomponent()
        super.onDestroy()
    }
}