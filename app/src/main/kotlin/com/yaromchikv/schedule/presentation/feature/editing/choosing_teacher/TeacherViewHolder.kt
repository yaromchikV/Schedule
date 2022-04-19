package com.yaromchikv.schedule.presentation.feature.editing.choosing_teacher

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.databinding.ItemListBinding

class TeacherViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(teacher: TeacherModel?, selectedId: Int) {
        with(binding) {
            if (teacher != null) {
                name.text = getFullName(teacher)
                checkButton.isVisible = selectedId == teacher.id
            } else {
                checkButton.isVisible = selectedId == -1
            }
        }
    }

    private fun getFullName(teacher: TeacherModel): String {
        var fullName = "${teacher.surname} ${teacher.name[0]}. ${teacher.patronymic[0]}."
        if (teacher.rank.isNotBlank()) {
            fullName += " (${teacher.rank})"
        }
        return fullName
    }
}