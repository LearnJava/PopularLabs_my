package ru.konstantin.popularlabs_my.ui.users

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.screens.AppScreens
import ru.konstantin.popularlabs_my.ui.base.IListPresenter

class UsersPresenter(
    private val router: Router,
    private val usersRepository: GithubUsersRepository,
    private val usersFragment: UsersFragment?
): MvpPresenter<UsersView>() {
    /** ИСХОДНЫЕ ДАННЫЕ */ //region
    // users
    private var users: List<GithubUserModel> = listOf()
    // usersListPresenter
    val usersListPresenter = UsersListPresenter()
    //endregion

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()

        usersFragment?.let { usersFragment ->
            usersListPresenter.itemClickListener = { userItemView ->
                val userModel: GithubUserModel = GithubUserModel(users[userItemView.pos].login,
                    users[userItemView.pos].avatarUrl, users[userItemView.pos].reposUrl)
                usersFragment.getMainActivity()?.let { mainActivity ->
                    mainActivity.setGithubUserModel(userModel)
                    mainActivity.setUsersModel(users)
                }
                router.navigateTo(AppScreens.repoScreen())
            }
        }
    }

    fun loadData() {
        usersRepository.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .subscribe(
                { users ->
                    this.users = users
                    usersFragment?.let { usersFragment ->
                        usersFragment.getMainActivity()?.let { mainActivity ->
                            mainActivity.setUsersModel(users)
                        }
                    }
                    viewState.updateList(users)
                    viewState.hideLoading()
                }, { e ->
                    Log.e("mylogs", "Ошибка при получении пользователей", e)
                    viewState.hideLoading()
                }
            )
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    class UsersListPresenter(): IListPresenter<UserItemView> {

        var users: MutableList<GithubUserModel> = mutableListOf<GithubUserModel>()

        override var itemClickListener: (UserItemView) -> Unit = {}

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            view.setAvatar(user.avatarUrl)
        }
    }

    fun setUsers(users: List<GithubUserModel>) {
        this.users = users
    }
}