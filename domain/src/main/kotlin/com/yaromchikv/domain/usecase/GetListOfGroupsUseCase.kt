package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.common.GroupMapperInterface
import com.yaromchikv.domain.model.GroupInterface
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListOfGroupsUseCase(
    private val repository: ScheduleRepository,
    private val mapper: GroupMapperInterface<GroupInterface, GroupModel>
) {
    operator fun invoke(): Flow<List<GroupModel>> {
        val dataFlow = repository.getGroups()
        return dataFlow.map { mapper.mapFromEntityList(it) }
    }
}