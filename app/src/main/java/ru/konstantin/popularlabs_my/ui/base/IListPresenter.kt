package ru.konstantin.popularlabs_my.ui.base

import ru.konstantin.popularlabs_my.ui.users.UserItemView

interface IListPresenter<V : IItemView> {

    var itemClickListener: (UserItemView) -> Unit

    fun getCount(): Int

    fun bindView(view: V)
}