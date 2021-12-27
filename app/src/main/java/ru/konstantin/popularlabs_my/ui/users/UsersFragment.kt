package ru.konstantin.popularlabs_my.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.konstantin.popularlabs_my.App
import ru.konstantin.popularlabs_my.databinding.FragmentUsersBinding
import ru.konstantin.popularlabs_my.domain.UserChooseRepository
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener
import ru.konstantin.popularlabs_my.ui.users.adapter.UsersAdapter
import ru.konstantin.popularlabs_my.ui.utils.GlideImageLoader

class UsersFragment: MvpAppCompatFragment(), UsersView, BackButtonListener {

    /** Задание переменных */ //region
    // userChoose
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()
    // presenter
    private val presenter by moxyPresenter {
        App.instance.appComponent.usersPresenter()
    }
    // binding
    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!
    // adapter
    private val adapter by lazy {
        UsersAdapter(
            presenter.usersListPresenter,
            GlideImageLoader()
        )
    }
    //endregion

    companion object {
        fun newInstance() = UsersFragment()
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
        /** Установка списка пользователей */
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

    override fun onResume() {
        super.onResume()

        presenter.setUsers(userChoose.getUsersModel())
    }
}