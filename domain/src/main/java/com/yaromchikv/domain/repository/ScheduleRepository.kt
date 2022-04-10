package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.model.LessonInterface
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int): Flow<List<LessonInterface>>
    fun getDaysOfWeek(): Flow<List<DayOfWeekInterface>>
}