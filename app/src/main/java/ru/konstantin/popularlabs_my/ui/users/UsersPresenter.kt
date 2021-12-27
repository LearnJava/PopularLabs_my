package ru.konstantin.popularlabs_my.ui.users

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
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus
import ru.konstantin.popularlabs_my.screens.AppScreens
import ru.konstantin.popularlabs_my.ui.base.IListPresenter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class UsersPresenter(
    private val router: Router,
    private val usersRepository: GithubUsersRepository,
    private val usersFragment: UsersFragment?,
    private val networkStatus: NetworkStatus
): MvpPresenter<UsersView>() {
    /** ИСХОДНЫЕ ДАННЫЕ */ //region
    // users
    private var users: List<GithubUserModel> = listOf()
    // usersListPresenter
    val usersListPresenter = UsersListPresenter(usersFragment, networkStatus)
    //endregion

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadData()

        usersFragment?.let { usersFragment ->
            usersListPresenter.itemClickListener = { userItemView ->
                val userModel: GithubUserModel = GithubUserModel(
                    users[userItemView.pos].id,
                    users[userItemView.pos].login,
                    users[userItemView.pos].avatarUrl,
                    users[userItemView.pos].reposUrl)
                usersFragment.getMainActivity()?.let { mainActivity ->
                    mainActivity.setGithubUserModel(userModel)
                    mainActivity.setUsersModel(users)
                }
                router.navigateTo(AppScreens.repoScreen())
            }
        }
    }

    fun loadData() {
        usersRepository.getCachedUsers()
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
                    Log.e("logsToMe", "Ошибка при получении пользователей", e)
                    viewState.hideLoading()
                }
            )
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    class UsersListPresenter(usersFragment: UsersFragment?, networkStatus: NetworkStatus):
        IListPresenter<UserItemView> {

        var users: MutableList<GithubUserModel> = mutableListOf<GithubUserModel>()
        private val usersFragment: UsersFragment? = usersFragment
        private var file: File = File("")
        private val networkStatus: NetworkStatus = networkStatus

        override var itemClickListener: (UserItemView) -> Unit = {}

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)

            usersFragment?.let { usersFragment ->
                if (networkStatus.isOnline()) {
                    view.setAvatar(user.avatarUrl)
                    usersFragment.getMainActivity()?.let { mainActivity ->
                        file = File("${mainActivity.getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES)}/CacheAvatars/${user.login}")

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
                                Glide.with(mainActivity)
                                    .asBitmap()
                                    .load(user.avatarUrl)
                                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                                    .error(android.R.drawable.stat_notify_error)
                                    .submit()
                                    .get(), file
                            )
                        }
                    }
                } else {
                    usersFragment.getMainActivity()?.let { mainActivity ->
                        file = File("${mainActivity.getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES)
                            }/CacheAvatars/${user.login}")
                        file = File(file, "${user.login}.jpg")
                        if ((file.exists()) && (file.length() > 0)) {
                            view.setAvatar(file.toString())
                        }
                    }
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