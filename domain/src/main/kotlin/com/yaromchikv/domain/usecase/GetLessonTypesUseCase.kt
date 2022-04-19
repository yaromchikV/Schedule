package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetLessonTypesUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<LessonTypeModel>> {
        return repository.getLessonTypes()
    }
}