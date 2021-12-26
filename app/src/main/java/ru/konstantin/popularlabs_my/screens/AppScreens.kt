package ru.konstantin.popularlabs_my.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.konstantin.popularlabs_my.ui.forks.ForksFragment
import ru.konstantin.popularlabs_my.ui.repos.ReposFragment
import ru.konstantin.popularlabs_my.ui.users.UsersFragment

object AppScreens {
    /** Вызов фрагмента со списком логинов пользователей */
    fun usersScreen() = FragmentScreen {
        UsersFragment.newInstance()
    }

    /** Вызов фрагмента с репозиторием пользователя */
    fun repoScreen() = FragmentScreen {
        ReposFragment.newInstance()
    }

    /** Вызов фрагмента с репозиторием пользователя */
    fun forksScreen() = FragmentScreen {
        ForksFragment.newInstance()
    }
}