package ru.konstantin.popularlabs_my.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.popularlabs_my.databinding.ItemUserBinding
import ru.konstantin.popularlabs_my.model.GithubUserModel
import ru.konstantin.popularlabs_my.ui.users.UserItemView
import ru.konstantin.popularlabs_my.ui.users.UsersPresenter

class UsersAdapter(
    private val presenter: UsersPresenter.UsersListPresenter
): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener(this) }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }

    inner class UserViewHolder(private val vb: ItemUserBinding): RecyclerView.ViewHolder(vb.root),
        UserItemView {

        override var pos: Int = -1

        override fun setLogin(login: String) {
            vb.tvLogin.text = login
        }
    }

    /** Метод и класс для динамического обновления списка */ //region
    fun submitList(newFavoriteData: List<GithubUserModel>) {
        val oldFavoriteData: List<GithubUserModel> = presenter.users
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(DiffCallback(oldFavoriteData, newFavoriteData))
        presenter.users.clear()
        newFavoriteData.forEach {
            presenter.users.add(it)
        }
        diffResult.dispatchUpdatesTo(this)
    }
    class DiffCallback(
        var oldList: List<GithubUserModel>,
        var newList: List<GithubUserModel>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
    //endregion
}