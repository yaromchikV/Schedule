package com.yaromchikv.data.mapper

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

    fun mapToClassroomModelList(list: List<ClassroomView>): List<ClassroomModel> {
        return list.map { mapToClassroomModel(it) }
    }
}