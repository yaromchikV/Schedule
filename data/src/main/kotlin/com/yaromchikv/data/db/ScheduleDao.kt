package com.yaromchikv.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yaromchikv.data.models.entity.BuildingEntity
import com.yaromchikv.data.models.entity.ClassroomEntity
import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.LessonTypeEntity
import com.yaromchikv.data.models.entity.RoleEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.data.models.entity.UserEntity
import com.yaromchikv.data.models.views.ClassroomView
import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.data.models.views.LessonView
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM LessonView WHERE day_id = :dayIndex AND group_id = :groupId ORDER BY start_time, subgroup")
    fun getLessonsForGroupByDay(dayIndex: Int, groupId: Int): Flow<List<LessonView>>

    @Query("SELECT * FROM GroupView ORDER BY name")
    fun getGroups(): Flow<List<GroupView>>

    @Query("SELECT * FROM ClassroomView ORDER BY building_id, number ASC")
    fun getClassrooms(): Flow<List<ClassroomView>>

    @Query("SELECT * FROM teachers ORDER BY surname, name, patronymic, rank ASC")
    fun getTeachers(): Flow<List<TeacherEntity>>

    @Query("SELECT * FROM days")
    fun getDaysOfWeek(): Flow<List<DayOfWeekEntity>>

    @Query("SELECT * FROM specialities")
    fun getSpecialities(): Flow<List<SpecialityEntity>>

    @Query("SELECT * FROM lesson_types")
    fun getLessonTypes(): Flow<List<LessonTypeEntity>>


    @Query("SELECT * FROM LessonView WHERE id = :id")
    suspend fun getLessonById(id: Int): LessonView?

    @Query("SELECT * FROM GroupView WHERE name = :name")
    suspend fun getGroupByName(name: String): GroupView?


    @Query("SELECT name FROM specialities WHERE id = :id")
    suspend fun getSpecialityNameById(id: Int): String?

    @Query("SELECT COUNT(*) FROM groups WHERE name = :name")
    suspend fun checkIfGroupExist(name: String): Boolean


    @Query("SELECT COUNT(*) FROM classrooms")
    suspend fun getCountOfClassrooms(): Int

    @Query("SELECT COUNT(*) FROM teachers")
    suspend fun getCountOfTeachers(): Int

    @Query("SELECT COUNT(*) FROM specialities")
    suspend fun getCountOfSpecialities(): Int


    @Query("SELECT id FROM classrooms WHERE number = :name AND building_id = :buildingId")
    suspend fun getClassroomIdByNameAndBuildingId(name: String, buildingId: Int): Int?

    @Query("SELECT id FROM users WHERE username = :username")
    suspend fun getIdByUsername(username: String): Int?

    @Query(
        "SELECT roles.role FROM users " +
                "JOIN roles ON roles.id = users.role_id " +
                "WHERE username = :username AND password = :password"
    )
    suspend fun getRoleByUsernameAndPassword(username: String, password: String): Int?


    @Insert
    suspend fun addGroup(groupEntity: GroupEntity)

    @Insert
    suspend fun addLesson(lessonEntity: LessonEntity)

    @Update
    suspend fun updateLesson(lessonEntity: LessonEntity)

    @Delete
    suspend fun deleteLesson(lessonEntity: LessonEntity)

    @Query("DELETE FROM lessons WHERE group_id = :id")
    suspend fun deleteLessonsByGroupId(id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfRoles(list: List<RoleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUsers(list: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfDaysOfWeek(list: List<DayOfWeekEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfLessonTypes(list: List<LessonTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfTeachers(list: List<TeacherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfClassrooms(list: List<ClassroomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfBuildings(list: List<BuildingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfSpecialities(list: List<SpecialityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfLessons(list: List<LessonEntity>)

}