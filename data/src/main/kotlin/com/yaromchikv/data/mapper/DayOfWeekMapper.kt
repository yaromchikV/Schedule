package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.domain.model.DayOfWeekModel

class DayOfWeekMapper {
    fun mapToDayOfWeekEntity(model: DayOfWeekModel): DayOfWeekEntity {
        return DayOfWeekEntity(
            id = model.id,
            name = model.name
        )
    }

    fun mapToDayOfWeekModel(entity: DayOfWeekEntity): DayOfWeekModel {
        return DayOfWeekModel(
            id = entity.id,
            name = entity.name
        )
    }

    fun mapToDayOfWeekModelList(list: List<DayOfWeekEntity>): List<DayOfWeekModel> {
        return list.map { mapToDayOfWeekModel(it) }
    }
}