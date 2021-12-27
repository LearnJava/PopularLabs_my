package ru.konstantin.popularlabs_my.ui.users

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.R
import ru.konstantin.popularlabs_my.di.scope.containers.UsersScopeContainer
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus
import ru.konstantin.popularlabs_my.screens.AppScreens
import ru.konstantin.popularlabs_my.ui.base.IListPresenter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val router: Router,
    private val usersRepository: GithubUsersRepository,
    private val networkStatus: NetworkStatus,
    private val appScreens: AppScreens,
    private val userChoose: UserChooseRepository,
    private val usersScopeContainer: UsersScopeContainer,
    private val context: Context
) : MvpPresenter<UsersView>() {
    /** Исходные данные */ //region
    // users
    private var users: List<GithubUserModel> = listOf()

    // usersListPresenter
    val usersListPresenter = UsersListPresenter(App.instance.applicationContext, networkStatus)
    //endregion

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()

        usersListPresenter.itemClickListener = { userItemView ->
            val userModel: GithubUserModel = GithubUserModel(
                users[userItemView.pos].id,
                users[userItemView.pos].login,
                users[userItemView.pos].avatarUrl,
                users[userItemView.pos].reposUrl
            )
            userChoose.setGithubUserModel(userModel)
            userChoose.setUsersModel(users)
            router.navigateTo(appScreens.repoScreen())
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
                    userChoose.setUsersModel(users)
                    viewState.updateList(users)
                    viewState.hideLoading()
                }, { e ->
                    Log.e("mylogs", "${context.getString(R.string.error_not_users_List)}", e)
                    viewState.hideLoading()
                }
            )
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    /** Уничтожение GithubUsersSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        usersScopeContainer.destroyGithubUsersSubcomponent()
        super.onDestroy()
    }

    class UsersListPresenter @Inject constructor(
        private val context: Context,
        private val networkStatus: NetworkStatus
    ) : IListPresenter<UserItemView> {

        var users: MutableList<GithubUserModel> = mutableListOf<GithubUserModel>()
        private var file: File = File("")

        override var itemClickListener: (UserItemView) -> Unit = {}

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)

            if (networkStatus.isOnline()) {
                view.setAvatar(user.avatarUrl)
                file = File(
                    "${
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    }/CacheAvatars/${user.login}"
                )

                /** Сохранение картинки в локальную папку с данным приложением */
                /** Создание директории, если она ещё не создана */
                if (!file.exists()) {
                    file.mkdirs()
                }
                file = File(file, "${user.login}.jpg")
                /** Создание файла */
                file.createNewFile()

                CoroutineScope(Dispatchers.IO).launch {
                    saveImage(
                        Glide.with(context)
                            .asBitmap()
                            .load(user.avatarUrl)
                            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                            .error(android.R.drawable.stat_notify_error)
                            .submit()
                            .get(), file
                    )
                }
            } else {
                file = File(
                    "${
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    }/CacheAvatars/${user.login}"
                )
                file = File(file, "${user.login}.jpg")
                if ((file.exists()) && (file.length() > 0)) {
                    view.setAvatar(file.toString())
                }
            }
        }

        /** Сохранение картинки */
        private fun saveImage(image: Bitmap, outPutFile: File) {
            try {
                val fOut: OutputStream = FileOutputStream(outPutFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setUsers(users: List<GithubUserModel>) {
        this.users = users
    }
}