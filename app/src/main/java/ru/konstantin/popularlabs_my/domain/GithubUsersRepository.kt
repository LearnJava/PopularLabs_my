package ru.konstantin.popularlabs_my.domain

import io.reactivex.rxjava3.core.Observable
import ru.konstantin.popularlabs_my.model.GithubUserModel

class GithubUsersRepository {
    private val users = listOf(
        GithubUserModel("Иванов Иван Иванович"),
        GithubUserModel("Софико Мила Юрьевна"),
        GithubUserModel("Васильев Ильнар Мазарович"),
        GithubUserModel("Гунар Кирилл Викторович"),
        GithubUserModel("Мирзо Юсуп Алиевич"),
        GithubUserModel("Ким Илья Андреевич")
    )

    fun getUsers(): Observable<List<GithubUserModel>> {
        return Observable.just(users)
    }
}