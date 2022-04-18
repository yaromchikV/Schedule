package com.yaromchikv.schedule.presentation.feature.change_group_menu

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemListBinding

class GroupViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(group: GroupModel) {
        with(binding) {
            name.text = root.context.getString(R.string.group_name, group.name, group.speciality)
            checkButton.isVisible = group.isSelected
        }
    }
}