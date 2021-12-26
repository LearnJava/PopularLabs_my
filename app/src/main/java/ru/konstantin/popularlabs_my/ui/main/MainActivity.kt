package ru.konstantin.popularlabs_my.ui.main

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.R
import ru.konstantin.popularlabs_my.databinding.ActivityMainBinding
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
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Producer().execFlatMap()
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
}