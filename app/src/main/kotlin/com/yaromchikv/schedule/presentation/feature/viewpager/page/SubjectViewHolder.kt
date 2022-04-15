package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemLessonBinding

class SubjectViewHolder(private val binding: ItemLessonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(lesson: LessonModel) {
        with(binding) {
            startTime.text = lesson.startTime
            endTime.text = lesson.endTime
            subject.text = root.context.getString(R.string.lesson_name, lesson.subject, lesson.type)
            classroom.text = lesson.classroom
            weeks.text = getWeeksText(lesson)
            teacher.text = getTeacherText(lesson)
            note.text = lesson.note

            note.visibility = if (lesson.note != null) View.VISIBLE else View.GONE
        }
    }

    private fun getWeeksText(lesson: LessonModel): String {
        val weeksList = lesson.weeks?.toMutableList()
        return if (weeksList?.contains(0) == false) {
            binding.root.context.getString(
                R.string.weeks_enum,
                weeksList.joinToString(separator = ", ")
            )
        } else ""
    }

    private fun getTeacherText(lesson: LessonModel): String {
        return if (lesson.subgroup != 0) {
            binding.root.context.getString(
                R.string.teacher_text,
                lesson.teacher,
                lesson.subgroup
            )
        } else lesson.teacher ?: ""
    }
}