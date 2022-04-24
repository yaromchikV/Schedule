package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.dto.ClassroomDto
import com.yaromchikv.data.models.entity.ClassroomEntity
import com.yaromchikv.data.models.views.ClassroomView
import com.yaromchikv.domain.model.ClassroomModel

class ClassroomMapper {
    fun mapToClassroomEntity(model: ClassroomModel): ClassroomEntity {
        return ClassroomEntity(
            id = model.id,
            number = model.number,
            buildingId = model.buildingId
        )
    }

    fun mapToClassroomModel(entity: ClassroomView): ClassroomModel {
        return ClassroomModel(
            id = entity.id,
            number = entity.number,
            buildingId = entity.buildingId,
            buildingName = entity.buildingName
        )
    }

    fun mapToClassroomModelFromDto(dto: ClassroomDto): ClassroomModel {
        return ClassroomModel(
            id = dto.id,
            number = dto.name,
            buildingId = dto.building.id,
            buildingName = dto.building.name
        )
    }

    fun mapToClassroomModelList(list: List<ClassroomView>): List<ClassroomModel> {
        return list.map { mapToClassroomModel(it) }
    }

    fun mapToClassroomEntityList(list: List<ClassroomModel>): List<ClassroomEntity> {
        return list.map { mapToClassroomEntity(it) }
    }

    fun mapToClassroomModelListFromDto(list: List<ClassroomDto>): List<ClassroomModel> {
        val resultList = mutableListOf<ClassroomModel>()
        list.forEach {
            if (it.name[0].isDigit() && it.building.name[0].isDigit()) {
                resultList.add(mapToClassroomModelFromDto(it))
            }
        }
        return resultList
    }
}