package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.domain.common.DomainMapper
import com.yaromchikv.domain.model.LessonModel

class LessonMapper : DomainMapper<LessonView, LessonModel> {
    override fun mapFromDomain(domainModel: LessonModel): LessonView {
        return LessonView(
            id = domainModel.id,
            subject = domainModel.subject,
            type = domainModel.type,
            note = domainModel.note,
            startTime = domainModel.startTime,
            endTime = domainModel.endTime,
            weeks = domainModel.weekNumber.joinToString(separator = ""),
            subgroupNum = domainModel.subgroupNum?.joinToString(separator = ""),
            teacher = domainModel.teacher,
            classroomName = domainModel.classroom
        )
    }

    override fun mapToDomain(entity: LessonView): LessonModel {
        return LessonModel(
            id = entity.id,
            subject = entity.subject,
            type = entity.type,
            note = entity.note,
            startTime = entity.startTime,
            endTime = entity.endTime,
            weekNumber = entity.weeks.toList().map { it.digitToInt() },
            subgroupNum = entity.subgroupNum?.toList()?.map { it.digitToInt() },
            teacher = entity.teacher,
            classroom = entity.classroomName
        )
    }

    override fun fromDomainList(list: List<LessonModel>): List<LessonView> {
        return list.map { mapFromDomain(it) }
    }

    override fun toDomainList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapToDomain(it) }
    }
}