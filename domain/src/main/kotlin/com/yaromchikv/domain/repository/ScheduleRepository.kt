package com.yaromchikv.domain.repository

import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int, groupId: Int): Flow<List<LessonModel>>
    fun getLessonById(id: Int): Flow<LessonModel?>
    fun getDaysOfWeek(): Flow<List<DayOfWeekModel>>
    fun getGroups(): Flow<List<GroupModel>>
    fun getTeachers(): Flow<List<TeacherModel>>
    fun getLessonTypes(): Flow<List<LessonTypeModel>>
    fun getClassrooms(): Flow<List<ClassroomModel>>

    fun getIdByUsername(username: String): Flow<Int?>
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

    suspend fun addLesson(lessonModel: LessonModel)
    suspend fun updateLesson(lessonModel: LessonModel)
    suspend fun deleteLesson(lessonModel: LessonModel)
}