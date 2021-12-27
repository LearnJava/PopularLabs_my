package ru.konstantin.popularlabs_my.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepoModel(
    @Expose
    val id: String,
    @Expose
    val name: String,
    @Expose
    val owner: GithubRepoOwner,
    @Expose
    val forksCount: Int
) : Parcelable

@Parcelize
data class GithubRepoOwner(
    @Expose
    val id: String
) : Parcelable
