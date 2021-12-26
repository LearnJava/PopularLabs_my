package ru.konstantin.popularlabs_my.ui.utils

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}