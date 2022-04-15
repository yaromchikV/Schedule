package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.common.LessonMapperInterface
import com.yaromchikv.domain.model.LessonInterface
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListOfLessonsUseCase(
    private val repository: ScheduleRepository,
    private val mapper: LessonMapperInterface<LessonInterface, LessonModel>
) {
    operator fun invoke(dayIndex: Int, groupName: String): Flow<List<LessonModel>> {
        val dataFlow = repository.getLessons(dayIndex, groupName)
        return dataFlow.map { mapper.mapFromEntityList(it) }
    }
}