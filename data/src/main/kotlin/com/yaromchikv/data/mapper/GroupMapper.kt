package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.domain.model.GroupModel

class GroupMapper {
    fun mapToGroupEntity(model: GroupModel): GroupEntity {
        return GroupEntity(
            id = model.id,
            name = model.name,
            specialityId = model.specialityId
        )
    }

    fun mapToGroupModel(entity: GroupView): GroupModel {
        return GroupModel(
            id = entity.id,
            name = entity.name,
            specialityId = entity.specialityId,
            speciality = entity.speciality
        )
    }

    fun mapFromGroupModelList(list: List<GroupView>): List<GroupModel> {
        return list.map { mapToGroupModel(it) }
    }
}