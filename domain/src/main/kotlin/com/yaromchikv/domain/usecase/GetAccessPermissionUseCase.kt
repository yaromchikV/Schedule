package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.repository.ScheduleRepository

class GetAccessPermissionUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int, username: String, password: String): Int? {
        val hash = GetHashPasswordUseCase().invoke("$id$password")
        return repository.getRoleByUsernameAndPassword(username, hash)
    }
}
