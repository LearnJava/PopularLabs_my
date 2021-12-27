package ru.konstantin.popularlabs_my.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.databinding.FragmentUsersBinding
import ru.konstantin.popularlabs_my.domain.GithubUsersRepositoryImpl
import ru.konstantin.popularlabs_my.domain.cache.RoomGithubUsersCache
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.remote.ApiHolder
import ru.konstantin.popularlabs_my.remote.connectivity.NetworkStatus
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener
import ru.konstantin.popularlabs_my.ui.main.MainActivity
import ru.konstantin.popularlabs_my.ui.users.adapter.UsersAdapter
import ru.konstantin.popularlabs_my.ui.utils.GlideImageLoader

class UsersFragment: MvpAppCompatFragment(), UsersView, BackButtonListener {

    private val status by lazy { NetworkStatus(requireContext().applicationContext) }

    /** Задание переменных */ //region
    private val presenter by moxyPresenter {
        UsersPresenter(
            App.instance.router,
            GithubUsersRepositoryImpl(RoomGithubUsersCache(status)),
            this@UsersFragment,
            status
        )
    }
    // binding
    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!
    // adapter
    private val adapter by lazy {
        UsersAdapter(presenter.usersListPresenter,
        GlideImageLoader())
    }
    // mainActivity
    private var mainActivity: MainActivity? = null
    //endregion

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = (context as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.usersListRecycler.adapter = adapter
    }

    override fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
        binding.usersListRecycler.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.loadingView.visibility = View.INVISIBLE
        binding.usersListRecycler.visibility = View.VISIBLE
    }

    override fun updateList(users: List<GithubUserModel>) {
        adapter.submitList(users)
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getMainActivity(): MainActivity? {
        return mainActivity
    }

    override fun onResume() {
        super.onResume()

        mainActivity?.let { mainActivity ->
            mainActivity.getUsersModel()?.let { users ->
                presenter.setUsers(users)
            }
        }
    }

    fun getNetworkStatus(): NetworkStatus {
        return status
    }
}