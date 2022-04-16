package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int, groupName: String): Flow<List<LessonModel>>
    fun getDaysOfWeek(): Flow<List<DayOfWeekModel>>
    fun getGroups(): Flow<List<GroupModel>>

    fun getIdByUsername(username: String): Flow<Int?>
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

}