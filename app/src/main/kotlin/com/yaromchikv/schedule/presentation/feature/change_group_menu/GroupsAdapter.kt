package com.yaromchikv.schedule.presentation.feature.change_group_menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.schedule.databinding.ItemGroupBinding

class GroupsAdapter : ListAdapter<GroupModel, GroupViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<GroupModel>() {
        override fun areItemsTheSame(oldItem: GroupModel, newItem: GroupModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GroupModel, newItem: GroupModel) =
            oldItem == newItem
    }

    private var onItemClickListener: ((GroupModel) -> Unit) = {}
    fun setOnItemClickListener(listener: (GroupModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGroupBinding.inflate(layoutInflater, parent, false)
        return GroupViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupModel = getItem(position)

        holder.bind(groupModel)
        holder.itemView.setOnClickListener {
            notifyDataSetChanged()
            onItemClickListener(groupModel)
        }
    }
}