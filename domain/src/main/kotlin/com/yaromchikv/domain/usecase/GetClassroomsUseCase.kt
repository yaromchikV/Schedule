package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetClassroomsUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<ClassroomModel>> {
        return repository.getClassrooms()
    }
}