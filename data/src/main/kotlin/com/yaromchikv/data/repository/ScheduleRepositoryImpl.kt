package com.yaromchikv.data.repository

import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDao
import com.yaromchikv.data.mapper.DayOfWeekMapper
import com.yaromchikv.data.mapper.GroupMapper
import com.yaromchikv.data.mapper.LessonMapper
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleRepositoryImpl(
    private val api: ScheduleApi,
    private val dao: ScheduleDao,
    private val lessonMapper: LessonMapper,
    private val groupMapper: GroupMapper,
    private val dayOfWeekMapper: DayOfWeekMapper
) : ScheduleRepository {

    override fun getLessons(dayIndex: Int, groupName: String): Flow<List<LessonModel>> {
        val dataFlow = dao.getLessonsByDay(dayIndex, groupName)
        return dataFlow.map { lessonMapper.mapToLessonModelList(it) }
    }

    override fun getDaysOfWeek(): Flow<List<DayOfWeekModel>> {
        val dataFlow = dao.getDaysOfWeek()
        return dataFlow.map { dayOfWeekMapper.mapToDayOfWeekModelList(it) }
    }

    override fun getGroups(): Flow<List<GroupModel>> {
        val dataFlow = dao.getGroups()
        return dataFlow.map { groupMapper.mapFromGroupModelList(it) }
    }

    override fun getIdByUsername(username: String): Flow<Int?> {
        return dao.getUsernameId(username)
    }

    override fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?> {
        return dao.getRoleByUsernameAndPassword(username, password)
    }
}