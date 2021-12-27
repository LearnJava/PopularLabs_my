package ru.konstantin.popularlabs_my.ui.users

import ru.konstantin.popularlabs_my.ui.base.IItemView

interface UserItemView : IItemView {

    fun setLogin(login: String)

    fun setAvatar(avatarUrl: String)
}