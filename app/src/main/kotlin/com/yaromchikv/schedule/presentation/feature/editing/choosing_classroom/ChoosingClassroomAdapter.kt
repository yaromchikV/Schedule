package com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.schedule.databinding.ItemListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID

class ChoosingClassroomAdapter :
    ListAdapter<ClassroomModel, ClassroomViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ClassroomModel>() {
        override fun areItemsTheSame(oldItem: ClassroomModel, newItem: ClassroomModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ClassroomModel, newItem: ClassroomModel) =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater, parent, false)
        return ClassroomViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ClassroomViewHolder, position: Int) {
        val classroom = getItem(position)

        holder.bind(classroom, selectedId)
        holder.itemView.setOnClickListener {
            selectedId = classroom?.id ?: NULL_ID
            notifyDataSetChanged()
            onItemClickListener(classroom)
        }
    }

    private var selectedId = NULL_ID
    fun setSelectedId(id: Int) {
        selectedId = id
    }

    private var onItemClickListener: ((ClassroomModel?) -> Unit) = {}
    fun setOnItemClickListener(listener: (ClassroomModel?) -> Unit) {
        onItemClickListener = listener
    }
}