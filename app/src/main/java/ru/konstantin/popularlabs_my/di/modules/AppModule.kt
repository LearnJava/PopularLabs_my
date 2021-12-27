package ru.konstantin.popularlabs_my.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.konstantin.popularlabs_my.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun context(): Context {
        return app
    }

    @Singleton
    @Provides
    fun app(): App {
        return app
    }
}