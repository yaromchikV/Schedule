package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class GetIdByUsernameUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(username: String): Flow<Int?> {
        return repository.getIdByUsername(username)
    }
}