package ru.konstantin.popularlabs_my.ui.forks

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.databinding.FragmentForksBinding
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener
import ru.konstantin.popularlabs_my.ui.main.MainActivity

class ForksFragment: MvpAppCompatFragment(), ForksView, BackButtonListener {
    /** ЗАДАНИЕ ПЕРЕМЕННЫХ */ //region
    // binding
    private var _binding: FragmentForksBinding? = null
    private val binding
        get() = _binding!!
    // presenter
    private val presenter by moxyPresenter {
        ForksPresenter(App.instance.router)
    }
    // mainActivity
    private var mainActivity: MainActivity? = null
    //endregion

    companion object {
        fun newInstance() = ForksFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (context as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentForksBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.let { mainActivity ->
            mainActivity.getGithubUserModel()?.let { userModel ->
                binding.repoTitle.text = "Информация о репозитории пользователя \"${userModel.login}\":"
            }
            mainActivity.getGithubRepoModel()?.let { repoModel ->
                binding.repoId.text = "ID: ${repoModel.id}"
                binding.repoName.text = "${repoModel.name}"
                binding.repoOwnerId.text = "ID владельца: ${repoModel.owner.id}"
                binding.forksNumber.text = "Количество форков: ${repoModel.forksCount}"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        mainActivity?.let { mainActivity ->
            mainActivity.getGithubUserModel()?.let { userModel ->
                binding.repoTitle.text = "Информация о репозитории пользователя \"${userModel.login}\":"
            }
            mainActivity.getGithubRepoModel()?.let { repoModel ->
                binding.repoId.text = "ID: ${repoModel.id}"
                binding.repoName.text = "${repoModel.name}"
                binding.repoOwnerId.text = "ID владельца: ${repoModel.owner.id}"
                binding.forksNumber.text = "Количество форков: ${repoModel.forksCount}"
            }
        }
    }

    override fun backPressed(): Boolean {
        presenter?.let { presenter ->
            presenter.backPressed()
        }
        return true
    }
}