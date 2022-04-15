package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.schedule.databinding.ItemLessonBinding

class ScheduleAdapter : ListAdapter<LessonModel, SubjectViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<LessonModel>() {
        override fun areItemsTheSame(oldItem: LessonModel, newItem: LessonModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonModel, newItem: LessonModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonBinding.inflate(layoutInflater, parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}