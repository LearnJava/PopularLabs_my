package ru.konstantin.popularlabs_my.ui.repos

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.domain.GithubRepoRepository
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.screens.AppScreens

class ReposPresenter(
    private val router: Router,
    private val repo: GithubRepoRepository,
    private val reposFragment: ReposFragment
): MvpPresenter<ReposView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()
    }

    private fun loadData() {
        reposFragment.getMainActivity()?.let { mainActivity ->
            val userModel: GithubUserModel = mainActivity.getGithubUserModel()
            userModel?.let { userModel ->
                repo.getRepos(userModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showLoading() }
                    .subscribe(
                        { repos ->
                            viewState.showRepos(repos)
                            viewState.hideLoading()
                            mainActivity.setReposModel(repos)
                        }, {
                            Log.e("logsToMe",
                                "Ошибка при получении репозиториев",
                                it
                            )
                            viewState.hideLoading()
                        }
                    )
            }
        }
    }

    fun onRepoClicked(repo: GithubRepoModel) {
        reposFragment.getMainActivity()?.let { mainActivity ->
            mainActivity.setGithubRepoModel(repo)
        }
        router.navigateTo(AppScreens.forksScreen())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}