package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.domain.model.TeacherModel

class TeacherMapper {
    fun mapToTeacherEntity(model: TeacherModel): TeacherEntity {
        return TeacherEntity(
            id = model.id,
            surname = model.surname,
            name = model.name,
            patronymic = model.patronymic,
            rank = model.rank
        )
    }

    fun mapToTeacherModel(entity: TeacherEntity): TeacherModel {
        return TeacherModel(
            id = entity.id ?: 0,
            surname = entity.surname,
            name = entity.name,
            patronymic = entity.patronymic,
            rank = entity.rank
        )
    }

    fun mapToTeacherModelList(list: List<TeacherEntity>): List<TeacherModel> {
        return list.map { mapToTeacherModel(it) }
    }
}