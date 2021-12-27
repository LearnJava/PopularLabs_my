package ru.konstantin.popularlabs_my

import android.app.Application
import ru.konstantin.popularlabs_my.di.components.AppComponent
import ru.konstantin.popularlabs_my.di.components.DaggerAppComponent
import ru.konstantin.popularlabs_my.di.modules.ContextModule

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!
    }
}