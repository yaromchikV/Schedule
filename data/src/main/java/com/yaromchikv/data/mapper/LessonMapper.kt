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
            weeks = domainModel.weeks?.joinToString(separator = ""),
            subgroup = domainModel.subgroup,
            teacher = domainModel.teacher,
            classroom = domainModel.classroom
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
            weeks = entity.weeks?.toList()?.map { it.digitToInt() },
            subgroup = entity.subgroup,
            teacher = entity.teacher,
            classroom = entity.classroom
        )
    }

    override fun fromDomainList(list: List<LessonModel>): List<LessonView> {
        return list.map { mapFromDomain(it) }
    }

    override fun toDomainList(list: List<LessonView>): List<LessonModel> {
        return list.map { mapToDomain(it) }
    }
}