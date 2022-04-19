package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.LessonTypeEntity
import com.yaromchikv.domain.model.LessonTypeModel

class LessonTypeMapper {
    fun mapToLessonTypeEntity(model: LessonTypeModel): LessonTypeEntity {
        return LessonTypeEntity(
            id = model.id,
            type = model.type
        )
    }

    fun mapToLessonTypeModel(entity: LessonTypeEntity): LessonTypeModel {
        return LessonTypeModel(
            id = entity.id ?: 0,
            type = entity.type
        )
    }

    fun mapToLessonTypeModelList(list: List<LessonTypeEntity>): List<LessonTypeModel> {
        return list.map { mapToLessonTypeModel(it) }
    }
}