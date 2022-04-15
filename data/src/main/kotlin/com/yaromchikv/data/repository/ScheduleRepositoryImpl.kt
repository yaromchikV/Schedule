package com.yaromchikv.data.repository

import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDao
import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.model.GroupInterface
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonInterface
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val api: ScheduleApi,
    private val dao: ScheduleDao
) : ScheduleRepository {

    override fun getLessons(dayIndex: Int, groupName: String): Flow<List<LessonInterface>> {
        return dao.getLessonsByDay(dayIndex, groupName)
    }

    override fun getDaysOfWeek(): Flow<List<DayOfWeekInterface>> {
        return dao.getDaysOfWeek()
    }

    override fun getGroups(): Flow<List<GroupInterface>> {
        return dao.getGroups()
    }

    override fun getIdByUsername(username: String): Flow<Int?> {
        return dao.getUsernameId(username)
    }

    override fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?> {
        return dao.getRoleByUsernameAndPassword(username, password)
    }
}