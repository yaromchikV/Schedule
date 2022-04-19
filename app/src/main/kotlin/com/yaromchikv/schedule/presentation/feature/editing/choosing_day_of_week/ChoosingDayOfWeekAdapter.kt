package com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.schedule.databinding.ItemListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID

class ChoosingDayOfWeekAdapter :
    ListAdapter<DayOfWeekModel, DayOfWeekViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<DayOfWeekModel>() {
        override fun areItemsTheSame(oldItem: DayOfWeekModel, newItem: DayOfWeekModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DayOfWeekModel, newItem: DayOfWeekModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfWeekViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater, parent, false)
        return DayOfWeekViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: DayOfWeekViewHolder, position: Int) {
        val dayOfWeek = getItem(position)

        holder.bind(dayOfWeek, selectedId)
        holder.itemView.setOnClickListener {
            selectedId = dayOfWeek?.id ?: NULL_ID
            notifyDataSetChanged()
            onItemClickListener(dayOfWeek)
        }
    }

    private var selectedId = NULL_ID
    fun setSelectedId(id: Int) {
        selectedId = id
    }

    private var onItemClickListener: ((DayOfWeekModel?) -> Unit) = {}
    fun setOnItemClickListener(listener: (DayOfWeekModel?) -> Unit) {
        onItemClickListener = listener
    }
}