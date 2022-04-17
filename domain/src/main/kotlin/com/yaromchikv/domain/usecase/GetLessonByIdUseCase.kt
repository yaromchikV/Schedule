package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetLessonByIdUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(id: Int): Flow<LessonModel> {
        return repository.getLessonById(id)
    }
}