package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.LessonInterface
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(): Flow<List<LessonInterface>>
}