package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.domain.common.LessonMapperInterface
import com.yaromchikv.domain.model.LessonModel

class LessonMapper : LessonMapperInterface<LessonView, LessonModel> {
    override fun mapToEntity(model: LessonModel): LessonView {
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

    override fun mapFromEntity(entity: LessonView): LessonModel {
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

    override fun mapToEntityList(list: List<LessonModel>): List<LessonView> {
        return list.map { mapToEntity(it) }
    }

    override fun mapFromEntityList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapFromEntity(it) }
    }
}