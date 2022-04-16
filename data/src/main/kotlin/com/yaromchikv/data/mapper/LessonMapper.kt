package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.domain.model.LessonModel

class LessonMapper {
    fun mapToLessonView(model: LessonModel): LessonView {
        return LessonView(
            id = model.id,
            subject = model.subject,
            type = model.type,
            note = model.note,
            startTime = model.startTime,
            endTime = model.endTime,
            weeks = model.weeks?.joinToString(separator = ""),
            subgroup = model.subgroup,
            teacher = model.teacher,
            classroom = model.classroom
        )
    }

    fun mapToLessonModel(entity: LessonView): LessonModel {
        return LessonModel(
            id = entity.id,
            subject = entity.subject,
            type = entity.type,
            note = entity.note,
            startTime = entity.startTime,
            endTime = entity.endTime,
            weeks = entity.weeks?.toList()?.map { it.digitToInt() },
            subgroup = entity.subgroup,
            teacher = entity.teacher,
            classroom = entity.classroom
        )
    }

    fun mapToLessonViewList(list: List<LessonModel>): List<LessonView> {
        return list.map { mapToLessonView(it) }
    }

    fun mapToLessonModelList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapToLessonModel(it) }
    }
}