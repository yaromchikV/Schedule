package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.dto.ScheduleDto
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.domain.model.LessonModel

class LessonMapper {
    fun mapToLessonEntity(model: LessonModel): LessonEntity {
        return LessonEntity(
            id = model.id,
            dayOfWeekId = model.dayOfWeekId ?: 0,
            classroomId = model.classroomId,
            weeks = model.weeks.joinToString(separator = ""),
            startTime = model.startTime,
            endTime = model.endTime,
            typeId = model.typeId ?: 0,
            subject = model.subject,
            note = model.note,
            groupId = model.groupId ?: 0,
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

    /*
    val id: Int? = null,
    val subject: String = "",
    val typeId: Int? = null,
    val type: String? = null,
    val note: String? = null,
    val startTime: String = "",
    val endTime: String = "",
    val dayOfWeekId: Int? = null,
    val dayOfWeek: String? = null,
    val weeks: List<Int> = emptyList(),
    val subgroup: Int = 0,
    val teacherId: Int? = null,
    val teacher: String? = null,
    val classroomId: Int? = null,
    val classroom: String? = null,
    val groupId: Int? = null
     */

//    fun mapToLessonListFromDto(dto: ScheduleDto): List<LessonModel> {
//        val result = mutableListOf<LessonModel>()
//        val groupId = dto.studentGroup?.id
//        dto.listOfDaySchedules.forEach {
//            it.weekDay
//        }
//
//
//        return result
//    }

    fun mapToLessonEntityList(list: List<LessonModel>): List<LessonEntity> {
        return list.map { mapToLessonEntity(it) }
    }

    fun mapToLessonModelList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapToLessonModel(it) }
    }
}