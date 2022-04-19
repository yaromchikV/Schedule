package com.yaromchikv.schedule.presentation.feature.editing.choosing_lesson_type

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.schedule.databinding.ItemListBinding

class LessonTypeViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(lessonType: LessonTypeModel?, selectedId: Int) {
        with(binding) {
            if (lessonType != null) {
                name.text = lessonType.type
                checkButton.isVisible = selectedId == lessonType.id
            } else {
                checkButton.isVisible = selectedId == -1
            }
        }
    }
}