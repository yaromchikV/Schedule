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

    fun getLessonsForGroupByDay(dayIndex: Int, groupId: Int): Flow<List<LessonModel>>
    fun getGroups(): Flow<List<GroupModel>>
    fun getClassrooms(): Flow<List<ClassroomModel>>
    fun getTeachers(): Flow<List<TeacherModel>>
    fun getDaysOfWeek(): Flow<List<DayOfWeekModel>>
    fun getSpecialities(): Flow<List<SpecialityModel>>
    fun getLessonTypes(): Flow<List<LessonTypeModel>>

    suspend fun getLessonById(id: Int): LessonModel?
    suspend fun getGroupByName(name: String): GroupModel?

    suspend fun getSpecialityNameById(id: Int): String?
    suspend fun checkIfGroupExist(name: String): Boolean

    suspend fun getCountOfClassrooms(): Int
    suspend fun getCountOfTeachers(): Int
    suspend fun getCountOfSpecialities(): Int

    suspend fun getClassroomIdByNameAndBuildingId(name: String, buildingId: Int): Int?
    suspend fun getIdByUsername(username: String): Int?
    suspend fun getRoleByUsernameAndPassword(username: String, password: String): Int?

    suspend fun addGroup(groupModel: GroupModel)
    suspend fun addLesson(lessonModel: LessonModel)
    suspend fun updateLesson(lessonModel: LessonModel)
    suspend fun deleteLesson(lessonModel: LessonModel)
    suspend fun deleteLessonsByGroupId(id: Int)

    suspend fun addTeachersList(list: List<TeacherModel>)
    suspend fun addClassroomList(list: List<ClassroomModel>)
    suspend fun addSpecialityList(list: List<SpecialityModel>)
    suspend fun addLessonList(list: List<LessonModel>)

    suspend fun getTeachersFromApi(): Result<List<TeacherModel>>
    suspend fun getClassroomsFromApi(): Result<List<ClassroomModel>>
    suspend fun getSpecialitiesFromApi(): Result<List<SpecialityModel>>
    suspend fun getScheduleFromApi(groupName: String): Result<ScheduleInterface>

}