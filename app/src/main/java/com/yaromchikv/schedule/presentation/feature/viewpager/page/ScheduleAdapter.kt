package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ItemLessonBinding

class ScheduleAdapter(
    private val context: Context
) : ListAdapter<LessonModel, ScheduleAdapter.SubjectViewHolder>(DiffCallback) {

    inner class SubjectViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: LessonModel) {
            val subjectText = context.getString(R.string.lesson_name, lesson.subject, lesson.type)

            val lessonWeeks = lesson.weeks?.toMutableList()
            val weeksText = if (lessonWeeks?.contains(0) == false) {
                lessonWeeks.remove(0)
                context.getString(R.string.weeks_enum, lessonWeeks.joinToString(separator = ", "))
            } else ""

            val teacherText = if (lesson.subgroup != 0) {
                context.getString(R.string.teacher_text, lesson.teacher, lesson.subgroup)
            } else lesson.teacher

            with(binding) {
                startTime.text = lesson.startTime
                endTime.text = lesson.endTime
                subject.text = subjectText
                classroom.text = lesson.classroom
                weeks.text = weeksText
                teacher.text = teacherText
                note.text = lesson.note

                note.visibility = if (lesson.note != null) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonBinding.inflate(layoutInflater, parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<LessonModel>() {
        override fun areItemsTheSame(oldItem: LessonModel, newItem: LessonModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonModel, newItem: LessonModel) =
            oldItem == newItem
    }
}