package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetIdByUsernameUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(username: String): Flow<Int?> {
        return repository.getIdByUsername(username)
    }
}