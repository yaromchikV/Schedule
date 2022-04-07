package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.schedule.databinding.ItemLessonBinding

class ScheduleAdapter :
    ListAdapter<LessonModel, ScheduleAdapter.SubjectViewHolder>(DiffCallback) {

    inner class SubjectViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: LessonModel) {

            with(binding) {
                startTime.text = lesson.startTime
                endTime.text = lesson.endTime
                subject.text = lesson.subject
                classroom.text = lesson.classroom
                weeks.text = lesson.weekNumber[0].toString()
                teacher.text = lesson.teacher
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