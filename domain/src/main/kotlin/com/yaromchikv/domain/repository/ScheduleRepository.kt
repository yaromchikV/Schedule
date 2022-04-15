package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.model.GroupInterface
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonInterface
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int, groupName: String): Flow<List<LessonInterface>>
    fun getDaysOfWeek(): Flow<List<DayOfWeekInterface>>
    fun getGroups(): Flow<List<GroupInterface>>

    fun getIdByUsername(username: String): Flow<Int?>
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

}