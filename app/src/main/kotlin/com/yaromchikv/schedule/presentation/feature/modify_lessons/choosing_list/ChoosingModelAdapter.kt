package com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.BaseModel
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.databinding.ItemListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID

class ChoosingModelAdapter :
    ListAdapter<BaseModel, BaseModelViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return (oldItem is ClassroomModel && newItem is ClassroomModel && oldItem == newItem) ||
                    (oldItem is DayOfWeekModel && newItem is DayOfWeekModel && oldItem == newItem) ||
                    (oldItem is LessonTypeModel && newItem is LessonTypeModel && oldItem == newItem) ||
                    (oldItem is TeacherModel && newItem is TeacherModel && oldItem == newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseModelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater, parent, false)
        return BaseModelViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BaseModelViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, selectedId)
        holder.itemView.setOnClickListener {
            selectedId = item?.id ?: NULL_ID
            notifyDataSetChanged()
            onItemClickListener(item)
        }
    }

    private var selectedId = NULL_ID
    fun setSelectedId(id: Int) {
        selectedId = id
    }

    private var onItemClickListener: ((BaseModel?) -> Unit) = {}
    fun setOnItemClickListener(listener: (BaseModel?) -> Unit) {
        onItemClickListener = listener
    }
}