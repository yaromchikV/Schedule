package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.dto.TeacherDto
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
            id = entity.id,
            surname = entity.surname,
            name = entity.name,
            patronymic = entity.patronymic,
            rank = entity.rank
        )
    }

    fun mapToTeacherModelFromDto(dto: TeacherDto): TeacherModel {
        return TeacherModel(
            id = dto.id,
            surname = dto.surname,
            name = dto.name,
            patronymic = dto.patronymic,
            rank = dto.rank ?: ""
        )
    }

    fun mapToTeacherEntityList(list: List<TeacherModel>): List<TeacherEntity> {
        return list.map { mapToTeacherEntity(it) }
    }

    fun mapToTeacherModelList(list: List<TeacherEntity>): List<TeacherModel> {
        return list.map { mapToTeacherModel(it) }
    }

    fun mapToTeacherModelListFromDto(list: List<TeacherDto>): List<TeacherModel> {
        return list.map { mapToTeacherModelFromDto(it) }
    }
}