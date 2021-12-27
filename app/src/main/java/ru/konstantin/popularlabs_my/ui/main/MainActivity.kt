package ru.konstantin.popularlabs_my.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.versionedparcelable.ParcelField
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.android.parcel.Parcelize
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.R
import ru.konstantin.popularlabs_my.databinding.ActivityMainBinding
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubRepoOwner
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.switchmap.Producer
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener

class MainActivity: MvpAppCompatActivity(), MainView {
    /** Задание переменных */ //region
    // binding
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!
    // navigator
    private val navigator = AppNavigator(this@MainActivity, R.id.container)
    // moxyPresenter
    private val presenter by moxyPresenter { MainPresenter(App.instance.router) }
    // githubUserModel
    private var githubUserModel: GithubUserModel =
        GithubUserModel("", "", "", "")
    private var users: List<GithubUserModel> = listOf()
    // githubRepoModel
    private var githubRepoModel: GithubRepoModel =
        GithubRepoModel("", "", GithubRepoOwner(""), 0)
    private var repos: List<GithubRepoModel> = listOf()
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Producer().execFlatMap()

        /** Получение разрешений на запись информации */
        presenter.isStoragePermissionGranted(this@MainActivity)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backPressed()
    }

    /** Получение и установка моделей */ //region
    fun setGithubUserModel(githubUserModel: GithubUserModel) {
        this.githubUserModel = githubUserModel
    }
    fun setUsersModel(users: List<GithubUserModel>) {
        this.users = users
    }
    fun getGithubUserModel(): GithubUserModel {
        return githubUserModel
    }
    fun getUsersModel(): List<GithubUserModel> {
        return users
    }
    fun setGithubRepoModel(githubRepoModel: GithubRepoModel) {
        this.githubRepoModel = githubRepoModel
    }
    fun setReposModel(repos: List<GithubRepoModel>) {
        this.repos = repos
    }
    fun getGithubRepoModel(): GithubRepoModel {
        return githubRepoModel
    }
    fun getReposModel(): List<GithubRepoModel> {
        return repos
    }
    //endregion


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("USER_MODEL_LOGIN", githubUserModel.login)
        outState.putString("USER_MODEL_AVATARURL", githubUserModel.avatarUrl)
        outState.putString("USER_MODEL_REPOSURL", githubUserModel.reposUrl)

        outState.putInt("NUMBER_USERS_MODEL", users.size)
        users.forEachIndexed { index, githubUserModel ->
            outState.putString("USER_MODEL_${index}_LOGIN", githubUserModel.login)
            outState.putString("USER_MODEL_${index}_AVATARURL", githubUserModel.avatarUrl)
            outState.putString("USER_MODEL_${index}_REPOSURL", githubUserModel.reposUrl)
        }

        outState.putString("REPO_MODEL_ID", githubRepoModel.id)
        outState.putString("REPO_MODEL_NAME", githubRepoModel.name)
        outState.putString("REPO_MODEL_OWNER_ID", githubRepoModel.owner.id)
        outState.putInt("REPO_MODEL_FORKSCOUNT", githubRepoModel.forksCount)

        outState.putInt("NUMBER_REPOS_MODEL", repos.size)
        repos.forEachIndexed { index, githubRepoModel ->
            outState.putString("REPO_MODEL_${index}_ID", githubRepoModel.id)
            outState.putString("REPO_MODEL_${index}_NAME", githubRepoModel.name)
            outState.putString("REPO_MODEL_${index}_OWNER_ID", githubRepoModel.owner.id)
            outState.putInt("REPO_MODEL_${index}_FORKSCOUNT", githubRepoModel.forksCount)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        githubUserModel = GithubUserModel(
            savedInstanceState.getString("USER_MODEL_ID") ?: "",
            savedInstanceState.getString("USER_MODEL_LOGIN") ?: "",
            savedInstanceState.getString("USER_MODEL_AVATARURL") ?: "",
            savedInstanceState.getString("USER_MODEL_REPOSURL") ?: ""
        )

        val numberUsers: Int = savedInstanceState.getInt("NUMBER_USERS_MODEL", -1)
        if (numberUsers > 0) {
            users = listOf()
            val newUsers: MutableList<GithubUserModel> = mutableListOf()
            repeat(numberUsers) { index ->
                newUsers.add(
                    GithubUserModel(
                        savedInstanceState.getString("USER_MODEL_${index}_ID") ?: "",
                        savedInstanceState.getString("USER_MODEL_${index}_LOGIN") ?: "",
                        savedInstanceState.getString("USER_MODEL_${index}_AVATARURL")
                            ?: "",
                        savedInstanceState.getString("USER_MODEL_${index}_REPOSURL")
                            ?: ""
                    )
                )
            }
            users = newUsers
        }

        githubRepoModel = GithubRepoModel(
            savedInstanceState.getString("REPO_MODEL_ID") ?: "",
            savedInstanceState.getString("REPO_MODEL_NAME") ?: "",
            GithubRepoOwner(savedInstanceState.getString("REPO_MODEL_OWNER_ID") ?: ""),
            savedInstanceState.getInt("REPO_MODEL_FORKSCOUNT") ?: -1,
        )

        val numberRepos: Int = savedInstanceState.getInt("NUMBER_REPOS_MODEL", -1)
        if (numberRepos > 0) {
            repos = listOf()
            val newRepos: MutableList<GithubRepoModel> = mutableListOf()
            repeat(numberUsers) { index ->
                newRepos.add(
                    GithubRepoModel(
                        savedInstanceState.getString("REPO_MODEL_${index}_ID") ?: "",
                        savedInstanceState.getString("REPO_MODEL_${index}_NAME") ?: "",
                        GithubRepoOwner(savedInstanceState.getString("REPO_MODEL_${
                            index}_OWNER_ID") ?: ""),
                        savedInstanceState.getInt("REPO_MODEL_${
                            index}_FORKSCOUNT") ?: -1,
                    )
                )
            }
            repos = newRepos
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this@MainActivity, "$message", Toast.LENGTH_LONG).show()
        Log.d("logsToMe", "$message")
    }
}