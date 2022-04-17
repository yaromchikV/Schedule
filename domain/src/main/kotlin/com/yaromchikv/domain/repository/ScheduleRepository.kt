package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int, groupId: Int): Flow<List<LessonModel>>
    fun getLessonById(id: Int): Flow<LessonModel>
    fun getDaysOfWeek(): Flow<List<DayOfWeekModel>>
    fun getGroups(): Flow<List<GroupModel>>

    fun getIdByUsername(username: String): Flow<Int?>
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

    suspend fun updateLesson(lessonModel: LessonModel)
}