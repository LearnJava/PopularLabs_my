package ru.konstantin.popularlabs_my.ui.repos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.R
import ru.konstantin.popularlabs_my.databinding.FragmentReposBinding
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener

class ReposFragment : MvpAppCompatFragment(), ReposView, BackButtonListener {
    /** ЗАДАНИЕ ПЕРЕМЕННЫХ */ //region
    // userChoose
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()

    // presenter
    private val presenter by moxyPresenter {
        App.instance.initGithubReposSubcomponent()
        App.instance.reposSubcomponent?.provideReposPresenter()!!
    }

    // binding
    private var _binding: FragmentReposBinding? = null
    private val binding
        get() = _binding!!

    // adapter
    private val adapter by lazy {
        ReposAdapter { presenter.onRepoClicked(it) }
    }
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReposBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Установка заголовка окна */
        binding.reposTitle.text = "${
            requireActivity().getString(R.string.repos_fragment_forks_title_text)
        } \"${
            userChoose.getGithubUserModel().login
        }\":"
        /** Установка списка репозиториев пользователя */
        binding.reposRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.reposRecycler.adapter = adapter
    }

    override fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingView.visibility = View.INVISIBLE
    }

    override fun showRepos(repos: List<GithubRepoModel>) {
        adapter.submitList(repos)
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    companion object {
        fun newInstance() = ReposFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        binding.reposTitle.text =
            "${requireActivity().getString(R.string.repos_fragment_forks_title_text)} \"${
                userChoose.getGithubUserModel().login
            }\":"
        binding.reposRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.reposRecycler.adapter = adapter
    }
}