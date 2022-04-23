package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.dto.SpecialityDto
import com.yaromchikv.data.models.dto.TeacherDto
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.domain.model.SpecialityModel
import com.yaromchikv.domain.model.TeacherModel

class SpecialityMapper {
    fun mapToSpecialityEntity(model: SpecialityModel): SpecialityEntity {
        return SpecialityEntity(
            id = model.id,
            name = model.name
        )
    }

    fun mapToSpecialityModel(entity: SpecialityEntity): SpecialityModel {
        return SpecialityModel(
            id = entity.id,
            name = entity.name
        )
    }

    fun mapToSpecialityModelFromDto(dto: SpecialityDto): SpecialityModel {
        return SpecialityModel(
            id = dto.id,
            name = dto.name
        )
    }

    fun mapToSpecialityEntityList(list: List<SpecialityModel>): List<SpecialityEntity> {
        return list.map { mapToSpecialityEntity(it) }
    }

    fun mapToSpecialityModelList(list: List<SpecialityEntity>): List<SpecialityModel> {
        return list.map { mapToSpecialityModel(it) }
    }

    fun mapToSpecialityModelListFromDto(list: List<SpecialityDto>): List<SpecialityModel> {
        return list.map { mapToSpecialityModelFromDto(it) }
    }
}