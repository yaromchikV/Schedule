package com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.BaseModel
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemListBinding

class BaseModelViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(baseModel: BaseModel?, selectedId: Int) {
        if (baseModel != null) {
            when (baseModel) {
                is ClassroomModel -> bindClassroom(baseModel, selectedId)
                is DayOfWeekModel -> bindDayOfWeek(baseModel, selectedId)
                is LessonTypeModel -> bindLessonType(baseModel, selectedId)
                is TeacherModel -> bindTeacher(baseModel, selectedId)
            }
        } else {
            binding.name.text = ""
            binding.checkButton.isVisible = selectedId == -1
        }
    }

    private fun bindClassroom(classroom: ClassroomModel, selectedId: Int) {
        with(binding) {
            name.text = root.context.getString(
                R.string.classroom_name,
                classroom.number,
                classroom.buildingName
            )
            checkButton.isVisible = selectedId == classroom.id
        }
    }

    private fun bindDayOfWeek(dayOfWeek: DayOfWeekModel, selectedId: Int) {
        with(binding) {
            name.text = dayOfWeek.name
            checkButton.isVisible = selectedId == dayOfWeek.id
        }
    }

    private fun bindLessonType(lessonType: LessonTypeModel, selectedId: Int) {
        with(binding) {
            name.text = lessonType.type
            checkButton.isVisible = selectedId == lessonType.id
        }
    }

    private fun bindTeacher(teacher: TeacherModel, selectedId: Int) {
        with(binding) {
            var fullName = "${teacher.surname} ${teacher.name[0]}. ${teacher.patronymic[0]}."
            if (teacher.rank.isNotBlank()) {
                fullName += " (${teacher.rank})"
            }

            name.text = fullName
            checkButton.isVisible = selectedId == teacher.id
        }
    }
}