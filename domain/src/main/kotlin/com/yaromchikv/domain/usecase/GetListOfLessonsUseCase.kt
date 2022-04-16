package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetListOfLessonsUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(dayIndex: Int, groupName: String): Flow<List<LessonModel>> {
        return repository.getLessons(dayIndex, groupName)
    }
}