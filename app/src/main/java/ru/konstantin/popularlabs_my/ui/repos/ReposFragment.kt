package ru.konstantin.popularlabs_my.ui.repos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.databinding.FragmentReposBinding
import ru.konstantin.popularlabs_my.domain.GithubRepoRepositoryImpl
import ru.konstantin.popularlabs_my.model.GithubRepoModel
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.ApiHolder
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener
import ru.konstantin.popularlabs_my.ui.main.MainActivity

class ReposFragment: MvpAppCompatFragment(), ReposView, BackButtonListener {
    /** ЗАДАНИЕ ПЕРЕМЕННЫХ */ //region
    // presenter
    private val presenter by moxyPresenter {
        ReposPresenter(
            router = App.instance.router,
            repo = GithubRepoRepositoryImpl(retrofitService = ApiHolder.retrofitService),
            this@ReposFragment
        )
    }
    // binding
    private var _binding: FragmentReposBinding? = null
    private val binding
        get() = _binding!!
    // adapter
    private val adapter by lazy {
        ReposAdapter { presenter.onRepoClicked(it) }
    }
    // mainActivity
    private var mainActivity: MainActivity? = null
    //endregion

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (context as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReposBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.let { mainActivity ->
            mainActivity.getGithubUserModel()?.let { userModel ->
                binding.reposTitle.text = "Список репозиториев\nпользователя \"${userModel.login}\":"
            }
        }
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

    fun getMainActivity(): MainActivity? {
        return mainActivity
    }

    override fun onResume() {
        super.onResume()

        mainActivity?.let { mainActivity ->
            mainActivity.getGithubUserModel()?.let { userModel ->
                binding.reposTitle.text = "Список репозиториев\nпользователя \"${userModel.login}\":"
            }
        }
        binding.reposRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.reposRecycler.adapter = adapter
    }
}