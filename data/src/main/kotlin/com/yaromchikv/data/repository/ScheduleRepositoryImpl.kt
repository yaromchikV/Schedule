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
import com.yaromchikv.domain.model.ScheduleInterface
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

    override fun getLessonsForGroupByDay(dayIndex: Int, groupId: Int): Flow<List<LessonModel>> {
        val lessonViewsFlow = dao.getLessonsForGroupByDay(dayIndex, groupId)
        return lessonViewsFlow.map { lessonMapper.mapToLessonModelList(it) }
    }

    override fun getGroups(): Flow<List<GroupModel>> {
        val groupViewsFlow = dao.getGroups()
        return groupViewsFlow.map { groupMapper.mapFromGroupModelList(it) }
    }

    override fun getClassrooms(): Flow<List<ClassroomModel>> {
        val classroomViewsFlow = dao.getClassrooms()
        return classroomViewsFlow.map { classroomMapper.mapToClassroomModelList(it) }
    }

    override fun getTeachers(): Flow<List<TeacherModel>> {
        val teachersFlow = dao.getTeachers()
        return teachersFlow.map { teacherMapper.mapToTeacherModelList(it) }
    }

    override fun getDaysOfWeek(): Flow<List<DayOfWeekModel>> {
        val daysOfWeekFlow = dao.getDaysOfWeek()
        return daysOfWeekFlow.map { dayOfWeekMapper.mapToDayOfWeekModelList(it) }
    }

    override fun getSpecialities(): Flow<List<SpecialityModel>> {
        val specialitiesFlow = dao.getSpecialities()
        return specialitiesFlow.map { specialityMapper.mapToSpecialityModelList(it) }
    }

    override fun getLessonTypes(): Flow<List<LessonTypeModel>> {
        val lessonTypesFlow = dao.getLessonTypes()
        return lessonTypesFlow.map { lessonTypeMapper.mapToLessonTypeModelList(it) }
    }

    override suspend fun getLessonById(id: Int): LessonModel? {
        val lessonView = dao.getLessonById(id)
        return lessonView?.let { lessonMapper.mapToLessonModel(it) }
    }

    override suspend fun getGroupByName(name: String): GroupModel? {
        val groupView = dao.getGroupByName(name)
        return groupView?.let { groupMapper.mapToGroupModel(it) }
    }

    override suspend fun getSpecialityNameById(id: Int): String? {
        return dao.getSpecialityNameById(id)
    }

    override suspend fun checkIfGroupExist(name: String): Boolean {
        return dao.checkIfGroupExist(name)
    }

    override suspend fun getCountOfClassrooms(): Int {
        return dao.getCountOfClassrooms()
    }

    override suspend fun getCountOfTeachers(): Int {
        return dao.getCountOfTeachers()
    }

    override suspend fun getCountOfSpecialities(): Int {
        return dao.getCountOfSpecialities()
    }

    override suspend fun getClassroomIdByNameAndBuildingId(name: String, buildingId: Int): Int? {
        return dao.getClassroomIdByNameAndBuildingId(name, buildingId)
    }

    override suspend fun getIdByUsername(username: String): Int? {
        return dao.getIdByUsername(username)
    }

    override suspend fun getRoleByUsernameAndPassword(username: String, password: String): Int? {
        return dao.getRoleByUsernameAndPassword(username, password)
    }

    override suspend fun addGroup(groupModel: GroupModel) {
        val groupEntity = groupMapper.mapToGroupEntity(groupModel)
        dao.addGroup(groupEntity)
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

    override suspend fun deleteLessonsByGroupId(id: Int) {
        dao.deleteLessonsByGroupId(id)
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
                Result.Error("Группа не найдена")
            }
        } catch (e: Exception) {
            Timber.i(e.cause)
            Result.Error("Ошибка подключения")
        }
    }
}