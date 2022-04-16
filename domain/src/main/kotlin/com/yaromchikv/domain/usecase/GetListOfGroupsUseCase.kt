package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetListOfGroupsUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<GroupModel>> {
        return repository.getGroups()
    }
}