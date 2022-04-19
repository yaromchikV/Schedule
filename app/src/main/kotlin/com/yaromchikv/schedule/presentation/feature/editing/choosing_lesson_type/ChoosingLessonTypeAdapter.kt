package com.yaromchikv.schedule.presentation.feature.editing.choosing_lesson_type

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.schedule.databinding.ItemListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID

class ChoosingLessonTypeAdapter :
    ListAdapter<LessonTypeModel, LessonTypeViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<LessonTypeModel>() {
        override fun areItemsTheSame(oldItem: LessonTypeModel, newItem: LessonTypeModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonTypeModel, newItem: LessonTypeModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater, parent, false)
        return LessonTypeViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LessonTypeViewHolder, position: Int) {
        val lessonType = getItem(position)

        holder.bind(lessonType, selectedId)
        holder.itemView.setOnClickListener {
            selectedId = lessonType?.id ?: NULL_ID
            notifyDataSetChanged()
            onItemClickListener(lessonType)
        }
    }

    private var selectedId = NULL_ID
    fun setSelectedId(id: Int) {
        selectedId = id
    }

    private var onItemClickListener: ((LessonTypeModel?) -> Unit) = {}
    fun setOnItemClickListener(listener: (LessonTypeModel?) -> Unit) {
        onItemClickListener = listener
    }
}