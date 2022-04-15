package com.yaromchikv.schedule.presentation.feature.change_group_menu

import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemGroupBinding

class GroupViewHolder(private val binding: ItemGroupBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(group: GroupModel, clickListener: (GroupModel) -> Unit) {
        with(binding) {
            groupName.text =
                root.context.getString(R.string.group_name, group.name, group.speciality)
        }

        itemView.setOnClickListener { clickListener(group) }
    }
}