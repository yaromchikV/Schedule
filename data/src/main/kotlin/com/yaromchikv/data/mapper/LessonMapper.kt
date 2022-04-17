package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.domain.model.LessonModel

class LessonMapper {
    fun mapToLessonEntity(model: LessonModel): LessonEntity {
        return LessonEntity(
            id = model.id,
            dayOfWeekId = model.dayOfWeekId,
            classroomId = model.classroomId,
            weeks = model.weeks.joinToString(separator = ""),
            startTime = model.startTime,
            endTime = model.endTime,
            typeId = model.typeId,
            subject = model.subject,
            note = model.note,
            groupId = model.groupId,
            subgroup = model.subgroup,
            teacherId = model.teacherId
        )
    }

    fun mapToLessonModel(entity: LessonView): LessonModel {
        return LessonModel(
            id = entity.id,
            subject = entity.subject,
            typeId = entity.typeId,
            type = entity.type,
            note = entity.note,
            startTime = entity.startTime,
            endTime = entity.endTime,
            dayOfWeekId = entity.dayOfWeekId,
            dayOfWeek = entity.dayOfWeek,
            weeks = entity.weeks.toList().map { it.digitToInt() },
            subgroup = entity.subgroup,
            teacherId = entity.teacherId,
            teacher = entity.teacher,
            classroomId = entity.classroomId,
            classroom = entity.classroom,
            groupId = entity.groupId
        )
    }

    fun mapToLessonModelList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapToLessonModel(it) }
    }
}