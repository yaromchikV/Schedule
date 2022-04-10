package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.common.DomainMapper
import com.yaromchikv.domain.model.LessonInterface
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListOfLessonsUseCase(
    private val repository: ScheduleRepository,
    private val mapper: DomainMapper<LessonInterface, LessonModel>
) {
    operator fun invoke(dayIndex: Int): Flow<List<LessonModel>> {
        val dataFlow = repository.getLessons(dayIndex)
        return dataFlow.map { mapper.toDomainList(it) }
    }
}