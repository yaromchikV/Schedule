package com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemListBinding

class ClassroomViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(classroom: ClassroomModel?, selectedId: Int) {
        with(binding) {
            if (classroom != null) {
                name.text = root.context.getString(
                    R.string.classroom_name,
                    classroom.number,
                    classroom.buildingName
                )
                checkButton.isVisible = selectedId == classroom.id
            } else {
                name.text = ""
                checkButton.isVisible = selectedId == -1
            }
        }
    }
}