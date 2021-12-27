package ru.konstantin.popularlabs_my.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.konstantin.popularlabs_my.screens.AppScreens

class MainPresenter(
    private val router: Router
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.replaceScreen(AppScreens.usersScreen())
    }

    fun backPressed() {
        router.exit()
    }

    /** Получение разрешений на запись и считывание информации с телефона */
    fun isStoragePermissionGranted(mainActivity: MainActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mainActivity.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                mainActivity.showMessage("Разрешение на запись и считывание данных получено")
                true
            } else {
                mainActivity.showMessage("Разрешение на запись и считывание данных отсутствует")
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
                false
            }
        } else {
            mainActivity.showMessage("Разрешение на запись и считывание данных получено")
            true
        }
    }
}