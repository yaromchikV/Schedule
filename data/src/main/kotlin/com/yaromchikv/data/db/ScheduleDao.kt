package com.yaromchikv.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yaromchikv.data.models.entity.BuildingEntity
import com.yaromchikv.data.models.entity.ClassroomEntity
import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.data.models.entity.FacultyEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.RoleEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.data.models.entity.UserEntity
import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.data.models.views.LessonView
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query(
        "SELECT lessons.id, subject, type, note, start_time, end_time, week_number, subgroup, " +
                "(teachers.surname || ' ' || substr(teachers.name, 1, 1) || '. ' || substr(teachers.patronymic, 1, 1) || '.') as teacher, (classrooms.number || '-' || buildings.name) as classroom_name " +
                "FROM lessons " +
                "JOIN groups ON groups.id = group_id " +
                "JOIN teachers ON teachers.id = teacher_id " +
                "JOIN classrooms ON classrooms.id = classroom_id " +
                "JOIN buildings ON buildings.id = classrooms.building_id " +
                "JOIN days ON days.id = day_of_week_id " +
                "WHERE day_of_week_id = :dayIndex AND groups.name = :groupName"
    )
    fun getLessonsByDay(dayIndex: Int, groupName: String): Flow<List<LessonView>>

    @Query(
        "SELECT groups.id, groups.name, specialities.name as speciality " +
                "FROM groups " +
                "JOIN specialities ON specialities.id = groups.speciality_id"
    )
    fun getGroups(): Flow<List<GroupView>>

    @Query("SELECT * FROM days")
    fun getDaysOfWeek(): Flow<List<DayOfWeekEntity>>

    @Query("SELECT id FROM users WHERE username = :username")
    fun getUsernameId(username: String): Flow<Int?>

    @Query(
        "SELECT roles.role " +
                "FROM users " +
                "JOIN roles ON roles.id = users.role_id " +
                "WHERE username = :username AND password = :password"
    )
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfRoles(list: List<RoleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUsers(list: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfDaysOfWeek(list: List<DayOfWeekEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfBuildings(list: List<BuildingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfClassrooms(list: List<ClassroomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfFaculties(list: List<FacultyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfSpecialities(list: List<SpecialityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfGroups(list: List<GroupEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfTeachers(list: List<TeacherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfLessons(list: List<LessonEntity>)

}