package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetAccessPermissionUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(id: Int, username: String, password: String): Flow<Int?> {
        val hash = GetHashPasswordUseCase().invoke("$id$password")
        return repository.getRoleByUsernameAndPassword(username, hash)
    }
}
