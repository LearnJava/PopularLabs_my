package ru.konstantin.popularlabs_my.ui.repos

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.domain.GithubRepoRepository
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.screens.AppScreens
import javax.inject.Inject

class ReposPresenter @Inject constructor(
    private val router: Router,
    private val repo: GithubRepoRepository,
    private val appScreens: AppScreens,
    private val userChoose: UserChooseRepository
): MvpPresenter<ReposView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()
    }

    private fun loadData() {
        val userModel: GithubUserModel = userChoose.getGithubUserModel()
        userModel?.let { userModel ->
            repo.getRepos(userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showLoading() }
                .subscribe(
                    { repos ->
                        viewState.showRepos(repos)
                        viewState.hideLoading()
                        userChoose.setReposModel(repos)
                    }, {
                        Log.e(
                            "mylogs",
                            "Ошибка при получении репозиториев",
                            it
                        )
                        viewState.hideLoading()
                    }
                )
        }
    }

    fun onRepoClicked(repo: GithubRepoModel) {
        userChoose.setGithubRepoModel(repo)
        router.navigateTo(appScreens.forksScreen())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}