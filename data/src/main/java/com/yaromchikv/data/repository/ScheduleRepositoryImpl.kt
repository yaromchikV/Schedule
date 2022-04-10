package com.yaromchikv.data.repository

import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDao
import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.model.LessonInterface
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val api: ScheduleApi,
    private val dao: ScheduleDao
) : ScheduleRepository {

    override fun getLessons(dayIndex: Int): Flow<List<LessonInterface>> {
        return dao.getLessons(dayIndex)
    }

    override fun getDaysOfWeek(): Flow<List<DayOfWeekInterface>> {
        return dao.getDaysOfWeek()
    }
}