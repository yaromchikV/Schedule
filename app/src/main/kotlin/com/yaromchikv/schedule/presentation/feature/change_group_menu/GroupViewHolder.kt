package com.yaromchikv.schedule.presentation.feature.change_group_menu

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemGroupBinding

class GroupViewHolder(private val binding: ItemGroupBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(group: GroupModel) {
        with(binding) {
            groupName.text =
                root.context.getString(R.string.group_name, group.name, group.speciality)

            checkButton.isVisible = group.isSelected
        }
    }
}