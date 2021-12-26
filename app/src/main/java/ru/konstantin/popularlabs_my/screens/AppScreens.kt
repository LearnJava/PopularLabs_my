package ru.konstantin.popularlabs_my.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.konstantin.popularlabs_my.ui.users.LoginFragment
import ru.konstantin.popularlabs_my.ui.users.UsersFragment

object AppScreens {
    /** Вызов фрагмента со списком логинов пользователей */
    fun usersScreen() = FragmentScreen {
        UsersFragment.newInstance()
    }
    /** Вызов фрагмента с логином пользователя */
    fun loginScreen(login: String) = FragmentScreen {
        LoginFragment.newInstance(login)
    }
}