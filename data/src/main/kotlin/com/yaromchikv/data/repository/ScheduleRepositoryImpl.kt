package com.yaromchikv.data.repository

import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDao
import com.yaromchikv.data.mapper.ClassroomMapper
import com.yaromchikv.data.mapper.DayOfWeekMapper
import com.yaromchikv.data.mapper.GroupMapper
import com.yaromchikv.data.mapper.LessonMapper
import com.yaromchikv.data.mapper.LessonTypeMapper
import com.yaromchikv.data.mapper.SpecialityMapper
import com.yaromchikv.data.mapper.TeacherMapper
import com.yaromchikv.data.models.entity.BuildingEntity
import com.yaromchikv.domain.common.Result
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.SpecialityModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.model.schedule.ScheduleInterface
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class ScheduleRepositoryImpl(
    private val api: ScheduleApi,
    private val dao: ScheduleDao,
    private val lessonMapper: LessonMapper,
    private val groupMapper: GroupMapper,
    private val dayOfWeekMapper: DayOfWeekMapper,
    private val teacherMapper: TeacherMapper,
    private val lessonTypeMapper: LessonTypeMapper,
    private val classroomMapper: ClassroomMapper,
    private val specialityMapper: SpecialityMapper
) : ScheduleRepository {

    override fun getLessons(dayIndex: Int, groupId: Int): Flow<List<LessonModel>> {
        val dataFlow = dao.getLessonsByDay(dayIndex, groupId)
        return dataFlow.map {
            Timber.i(it.toString())
            lessonMapper.mapToLessonModelList(it)
        }
    }

    override fun getLessonById(id: Int): Flow<LessonModel?> {
        val dataFlow = dao.getLessonById(id)
        return dataFlow.map {
            if (it != null) {
                lessonMapper.mapToLessonModel(it)
            } else null
        }
    }

    override fun getDaysOfWeek(): Flow<List<DayOfWeekModel>> {
        val dataFlow = dao.getDaysOfWeek()
        return dataFlow.map { dayOfWeekMapper.mapToDayOfWeekModelList(it) }
    }

    override fun getGroups(): Flow<List<GroupModel>> {
        val dataFlow = dao.getGroups()
        return dataFlow.map { groupMapper.mapFromGroupModelList(it) }
    }

    override fun getCountOfTeachers(): Flow<Int> {
        return dao.getCountOfTeachers()
    }

    override fun getTeachers(): Flow<List<TeacherModel>> {
        val dataFlow = dao.getTeachers()
        return dataFlow.map { teacherMapper.mapToTeacherModelList(it) }
    }

    override fun getLessonTypes(): Flow<List<LessonTypeModel>> {
        val dataFlow = dao.getLessonTypes()
        return dataFlow.map { lessonTypeMapper.mapToLessonTypeModelList(it) }
    }

    override fun getCountOfClassrooms(): Flow<Int> {
        return dao.getCountOfClassrooms()
    }

    override fun getCountOfSpecialities(): Flow<Int> {
        return dao.getCountOfSpecialities()
    }

    override fun getCountOfGroupByName(name: String): Flow<Int> {
        return dao.getCountOfGroupsByName(name)
    }

    override fun getGroupByName(name: String): Flow<GroupModel> {
        val groupView = dao.getGroupByName(name)
        return groupView.map { groupMapper.mapToGroupModel(it) }
    }

    override fun getClassrooms(): Flow<List<ClassroomModel>> {
        val dataFlow = dao.getClassrooms()
        return dataFlow.map { classroomMapper.mapToClassroomModelList(it) }
    }

    override fun getSpecialities(): Flow<List<SpecialityModel>> {
        val dataFlow = dao.getSpecialities()
        return dataFlow.map { specialityMapper.mapToSpecialityModelList(it) }
    }

    override suspend fun addLesson(lessonModel: LessonModel) {
        val lessonEntity = lessonMapper.mapToLessonEntity(lessonModel)
        dao.addLesson(lessonEntity)
    }

    override suspend fun updateLesson(lessonModel: LessonModel) {
        val lessonEntity = lessonMapper.mapToLessonEntity(lessonModel)
        dao.updateLesson(lessonEntity)
    }

    override suspend fun deleteLesson(lessonModel: LessonModel) {
        val lessonEntity = lessonMapper.mapToLessonEntity(lessonModel)
        dao.deleteLesson(lessonEntity)
    }

    override suspend fun addTeachersList(list: List<TeacherModel>) {
        val entities = teacherMapper.mapToTeacherEntityList(list)
        dao.insertListOfTeachers(entities)
    }

    override suspend fun addClassroomList(list: List<ClassroomModel>) {
        val buildings = mutableListOf<BuildingEntity>()
        list.map { classroom ->
            if (buildings.indexOfFirst { it.id == classroom.buildingId } == -1) {
                buildings.add(
                    BuildingEntity(
                        id = classroom.buildingId,
                        name = classroom.buildingName
                    )
                )
            }
        }
        dao.insertListOfBuildings(buildings)

        val entities = classroomMapper.mapToClassroomEntityList(list)
        dao.insertListOfClassrooms(entities)
    }

    override suspend fun addSpecialityList(list: List<SpecialityModel>) {
        val entities = specialityMapper.mapToSpecialityEntityList(list)
        dao.insertListOfSpecialities(entities)
    }

    override suspend fun addLessonList(list: List<LessonModel>) {
        val entities = lessonMapper.mapToLessonEntityList(list)
        dao.insertListOfLessons(entities)
    }

    override suspend fun addGroup(groupModel: GroupModel) {
        val groupEntity = groupMapper.mapToGroupEntity(groupModel)
        dao.addGroup(groupEntity)
    }

    override suspend fun deleteLessonByGroupId(id: Int) {
        dao.deleteLessonByGroupId(id)
    }

    override fun getIdByUsername(username: String): Flow<Int?> {
        return dao.getUsernameId(username)
    }

    override fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?> {
        return dao.getRoleByUsernameAndPassword(username, password)
    }

    override suspend fun getTeachersFromApi(): Result<List<TeacherModel>> {
        return try {
            val response = api.getTeachers()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val list = teacherMapper.mapToTeacherModelListFromDto(body)
                Result.Success(list)
            } else {
                Result.Error(response.message())
            }
        } catch (e: Exception) {
            Result.Error("Ошибка подключения")
        }
    }

    override suspend fun getClassroomsFromApi(): Result<List<ClassroomModel>> {
        return try {
            val response = api.getClassrooms()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val list = classroomMapper.mapToClassroomModelListFromDto(body)
                Result.Success(list)
            } else {
                Result.Error(response.message())
            }
        } catch (e: Exception) {
            Result.Error("Ошибка подключения")
        }
    }

    override suspend fun getSpecialitiesFromApi(): Result<List<SpecialityModel>> {
        return try {
            val response = api.getSpecialities()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val list = specialityMapper.mapToSpecialityModelListFromDto(body)
                Result.Success(list)
            } else {
                Result.Error(response.message())
            }
        } catch (e: Exception) {
            Result.Error("Ошибка подключения")
        }
    }

    override suspend fun getScheduleFromApi(groupName: String): Result<ScheduleInterface> {
        return try {
            val response = api.getSchedule(groupName)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Error(response.message())
            }
        } catch (e: Exception) {
            Result.Error("Ошибка подключения")
        }
    }

    override fun getClassroomIdByNameAndBuildingId(name: String, buildingId: Int): Flow<Int?> {
        return dao.getClassroomIdByNameAndBuildingId(name, buildingId)
    }

    override fun getSpecialityNameById(id: Int): Flow<String?> {
        return dao.getSpecialityNameById(id)
    }
}