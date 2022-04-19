package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetLessonsUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(dayIndex: Int, groupId: Int): Flow<List<LessonModel>> {
        return repository.getLessons(dayIndex, groupId)
    }
}