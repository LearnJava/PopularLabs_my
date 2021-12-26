package ru.konstantin.popularlabs_my.ui.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface UsersView: MvpView {

    @AddToEndSingle
    fun updateList()
}