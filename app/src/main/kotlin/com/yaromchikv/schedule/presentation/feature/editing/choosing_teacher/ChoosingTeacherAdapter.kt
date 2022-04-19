package com.yaromchikv.schedule.presentation.feature.editing.choosing_teacher

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.databinding.ItemListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID

class ChoosingTeacherAdapter :
    ListAdapter<TeacherModel, TeacherViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<TeacherModel>() {
        override fun areItemsTheSame(oldItem: TeacherModel, newItem: TeacherModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TeacherModel, newItem: TeacherModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater, parent, false)
        return TeacherViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = getItem(position)

        holder.bind(teacher, selectedId)
        holder.itemView.setOnClickListener {
            selectedId = teacher?.id ?: NULL_ID
            notifyDataSetChanged()
            onItemClickListener(teacher)
        }
    }

    private var selectedId = NULL_ID
    fun setSelectedId(id: Int) {
        selectedId = id
    }

    private var onItemClickListener: ((TeacherModel?) -> Unit) = {}
    fun setOnItemClickListener(listener: (TeacherModel?) -> Unit) {
        onItemClickListener = listener
    }
}