package ru.konstantin.popularlabs_my.ui.repos

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.konstantin.popularlabs_my.model.GithubRepoModel

@AddToEndSingle
interface ReposView : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showRepos(repos: List<GithubRepoModel>)
}