package ru.konstantin.popularlabs_my.domain

import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubRepoOwner
import ru.konstantin.popularlabs_my.model.GithubUserModel

class UserChooseRepositoryImpl : UserChooseRepository {
    /** Исходные данные */ //region
    // githubUserModel
    private var githubUserModel: GithubUserModel =
        GithubUserModel("", "", "", "")
    private var users: List<GithubUserModel> = listOf()

    // githubRepoModel
    private var githubRepoModel: GithubRepoModel =
        GithubRepoModel("", "", GithubRepoOwner(""), 0)
    private var repos: List<GithubRepoModel> = listOf()
    //endregion

    override fun setGithubUserModel(githubUserModel: GithubUserModel) {
        this.githubUserModel = githubUserModel
    }

    override fun setUsersModel(users: List<GithubUserModel>) {
        this.users = users
    }

    override fun getGithubUserModel(): GithubUserModel {
        return githubUserModel
    }

    override fun getUsersModel(): List<GithubUserModel> {
        return users
    }

    override fun setGithubRepoModel(githubRepoModel: GithubRepoModel) {
        this.githubRepoModel = githubRepoModel
    }

    override fun setReposModel(repos: List<GithubRepoModel>) {
        this.repos = repos
    }

    override fun getGithubRepoModel(): GithubRepoModel {
        return githubRepoModel
    }
}