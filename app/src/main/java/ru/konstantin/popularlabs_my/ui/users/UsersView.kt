package ru.konstantin.popularlabs_my.ui.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface UsersView : MvpView {

    @AddToEndSingle
    fun updateList(users: List<GithubUserModel>)

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()
}