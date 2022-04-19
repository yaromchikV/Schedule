package com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.schedule.databinding.ItemListBinding

class DayOfWeekViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dayOfWeek: DayOfWeekModel?, selectedId: Int) {
        with(binding) {
            if (dayOfWeek != null) {
                name.text = dayOfWeek.name
                checkButton.isVisible = selectedId == dayOfWeek.id
            } else {
                name.text = ""
                checkButton.isVisible = selectedId == -1
            }
        }
    }
}