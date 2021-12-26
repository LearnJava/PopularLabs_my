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
import ru.konstantin.popularlabs_my.domain.GithubUsersRepository
import ru.konstantin.popularlabs_my.ui.base.BackButtonListener
import ru.konstantin.popularlabs_my.ui.users.adapter.UsersAdapter

class UsersFragment: MvpAppCompatFragment(), UsersView, BackButtonListener {

    /** Задание переменных */ //region
    private val presenter by moxyPresenter {
        UsersPresenter(
            App.instance.router,
            GithubUsersRepository(),
            this@UsersFragment
        )
    }
    // binding
    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!
    // adapter
    private val adapter by lazy {
        UsersAdapter(presenter.usersListPresenter)
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

        binding.usersListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.usersListRecycler.adapter = adapter
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /** Получение адаптера */
    @JvmName("getAdapter1")
    fun getAdapter(): UsersAdapter {
        return adapter
    }
}