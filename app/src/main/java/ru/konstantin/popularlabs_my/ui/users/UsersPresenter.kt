package ru.konstantin.popularlabs_my.ui.users

import android.widget.Toast
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observable
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.R
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.screens.AppScreens
import ru.konstantin.popularlabs_my.ui.base.IListPresenter

class UsersPresenter(
    private val router: Router,
    private val usersRepository: GithubUsersRepository,
    private val usersFragment: UsersFragment?
): MvpPresenter<UsersView>() {

    val usersListPresenter = UsersListPresenter()
    private var users: List<GithubUserModel> = listOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()

        usersFragment?.let { usersFragment ->
            usersListPresenter.itemClickListener = { userItemView ->
                router.navigateTo(
                    AppScreens.loginScreen(
                        (if (userItemView.pos < users.size) users[userItemView.pos].login
                        else usersFragment.resources.getString(R.string.error_not_user_name))
                    )
                )
            }
        }
    }

    private fun loadData() {
        usersFragment?.let { usersFragment ->
            usersRepository.getUsers()
                .switchMap {
                    return@switchMap Observable.just( it )}
                .subscribe(
                    {
                        users = it
                        usersFragment.getAdapter().submitList(it)
                    },
                    {
                        Toast.makeText(usersFragment.requireContext(), "${
                            usersFragment.resources.getString(R.string.error_get_list_users)}$it",
                            Toast.LENGTH_LONG).show()
                    }
                )
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    class UsersListPresenter: IListPresenter<UserItemView> {

        val users = mutableListOf<GithubUserModel>()

        override var itemClickListener: (UserItemView) -> Unit = {}

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }
}