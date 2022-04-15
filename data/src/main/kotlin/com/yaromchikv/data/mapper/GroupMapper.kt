package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.domain.common.GroupMapperInterface
import com.yaromchikv.domain.model.GroupModel

class GroupMapper : GroupMapperInterface<GroupView, GroupModel> {
    override fun mapToEntity(model: GroupModel): GroupView {
        return GroupView(
            id = model.id,
            name = model.name,
            speciality = model.speciality
        )
    }

    override fun mapFromEntity(entity: GroupView): GroupModel {
        return GroupModel(
            id = entity.id,
            name = entity.name,
            speciality = entity.speciality
        )
    }

    override fun mapToEntityList(list: List<GroupModel>): List<GroupView> {
        return list.map { mapToEntity(it) }
    }

    override fun mapFromEntityList(list: List<GroupView>): List<GroupModel> {
        return list.map { mapFromEntity(it) }
    }
}