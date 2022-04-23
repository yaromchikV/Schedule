package com.yaromchikv.domain.repository

import com.yaromchikv.domain.common.Result
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.SpecialityModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.model.schedule.ScheduleInterface
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getLessons(dayIndex: Int, groupId: Int): Flow<List<LessonModel>>
    fun getLessonById(id: Int): Flow<LessonModel?>
    fun getDaysOfWeek(): Flow<List<DayOfWeekModel>>
    fun getGroups(): Flow<List<GroupModel>>
    fun getCountOfTeachers(): Flow<Int>
    fun getTeachers(): Flow<List<TeacherModel>>
    fun getLessonTypes(): Flow<List<LessonTypeModel>>
    fun getCountOfClassrooms(): Flow<Int>
    fun getCountOfSpecialities(): Flow<Int>
    fun getCountOfGroupByName(name: String): Flow<Int>
    fun getGroupByName(name: String): Flow<GroupModel>
    fun getClassrooms(): Flow<List<ClassroomModel>>
    fun getSpecialities(): Flow<List<SpecialityModel>>

    fun getIdByUsername(username: String): Flow<Int?>
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

    suspend fun addLesson(lessonModel: LessonModel)
    suspend fun updateLesson(lessonModel: LessonModel)
    suspend fun deleteLesson(lessonModel: LessonModel)
    suspend fun addTeachersList(list: List<TeacherModel>)
    suspend fun addClassroomList(list: List<ClassroomModel>)
    suspend fun addSpecialityList(list: List<SpecialityModel>)
    suspend fun addLessonList(list: List<LessonModel>)
    suspend fun addGroup(groupModel: GroupModel)
    suspend fun deleteLessonByGroupId(id: Int)

    suspend fun getTeachersFromApi(): Result<List<TeacherModel>>
    suspend fun getClassroomsFromApi(): Result<List<ClassroomModel>>
    suspend fun getSpecialitiesFromApi(): Result<List<SpecialityModel>>
    suspend fun getScheduleFromApi(groupName: String): Result<ScheduleInterface>

    fun getClassroomIdByNameAndBuildingId(name: String, buildingId: Int): Flow<Int?>
    fun getSpecialityNameById(id: Int): Flow<String?>

}