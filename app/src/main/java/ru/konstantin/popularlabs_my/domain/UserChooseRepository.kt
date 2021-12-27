package ru.konstantin.popularlabs_my.domain

import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel

interface UserChooseRepository {
    fun setGithubUserModel(githubUserModel: GithubUserModel)
    fun setUsersModel(users: List<GithubUserModel>)
    fun getGithubUserModel(): GithubUserModel
    fun getUsersModel(): List<GithubUserModel>
    fun setGithubRepoModel(githubRepoModel: GithubRepoModel)
    fun setReposModel(repos: List<GithubRepoModel>)
    fun getGithubRepoModel(): GithubRepoModel
}