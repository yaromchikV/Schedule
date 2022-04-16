package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.domain.model.DayOfWeekModel

class DayOfWeekMapper {
    fun mapToDayOfWeekEntity(model: DayOfWeekModel): DayOfWeekEntity {
        return DayOfWeekEntity(
            id = model.id,
            name = model.name,
            abbrev = model.abbrev
        )
    }

    fun mapToDayOfWeekModel(entity: DayOfWeekEntity): DayOfWeekModel {
        return DayOfWeekModel(
            id = entity.id ?: 0,
            name = entity.name,
            abbrev = entity.abbrev
        )
    }

    fun mapToDayOfWeekEntityList(list: List<DayOfWeekModel>): List<DayOfWeekEntity> {
        return list.map { mapToDayOfWeekEntity(it) }
    }

    fun mapToDayOfWeekModelList(list: List<DayOfWeekEntity>): List<DayOfWeekModel> {
        return list.map { mapToDayOfWeekModel(it) }
    }
}