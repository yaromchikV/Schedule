package com.yaromchikv.data.mapper

import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.domain.model.GroupModel

class GroupMapper {
    fun mapToGroupView(model: GroupModel): GroupView {
        return GroupView(
            id = model.id,
            name = model.name,
            speciality = model.speciality
        )
    }

    fun mapToGroupModel(entity: GroupView): GroupModel {
        return GroupModel(
            id = entity.id,
            name = entity.name,
            speciality = entity.speciality
        )
    }

    fun mapFromGroupModelList(list: List<GroupView>): List<GroupModel> {
        return list.map { mapToGroupModel(it) }
    }
}