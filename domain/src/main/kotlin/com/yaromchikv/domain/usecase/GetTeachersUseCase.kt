package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetTeachersUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<TeacherModel>> {
        return repository.getTeachers()
    }
}