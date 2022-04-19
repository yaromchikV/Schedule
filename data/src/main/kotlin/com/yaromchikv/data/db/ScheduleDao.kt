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
import com.yaromchikv.data.models.entity.FacultyEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.RoleEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.data.models.entity.LessonTypeEntity
import com.yaromchikv.data.models.entity.UserEntity
import com.yaromchikv.data.models.views.ClassroomView
import com.yaromchikv.data.models.views.GroupView
import com.yaromchikv.data.models.views.LessonView
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM LessonView WHERE day_id = :dayIndex AND group_id = :groupId")
    fun getLessonsByDay(dayIndex: Int, groupId: Int): Flow<List<LessonView>>

    @Query("SELECT * FROM LessonView WHERE id = :id")
    fun getLessonById(id: Int): Flow<LessonView?>

    @Query("SELECT * FROM GroupView")
    fun getGroups(): Flow<List<GroupView>>

    @Query("SELECT * FROM days")
    fun getDaysOfWeek(): Flow<List<DayOfWeekEntity>>

    @Query("SELECT * FROM teachers")
    fun getTeachers(): Flow<List<TeacherEntity>>

    @Query("SELECT * FROM ClassroomView")
    fun getClassrooms(): Flow<List<ClassroomView>>

    @Query("SELECT * FROM lesson_types")
    fun getLessonTypes(): Flow<List<LessonTypeEntity>>

    @Query("SELECT id FROM users WHERE username = :username")
    fun getUsernameId(username: String): Flow<Int?>

    @Query(
        "SELECT roles.role " +
                "FROM users " +
                "JOIN roles ON roles.id = users.role_id " +
                "WHERE username = :username AND password = :password"
    )
    fun getRoleByUsernameAndPassword(username: String, password: String): Flow<Int?>

    @Update
    suspend fun updateLesson(lessonEntity: LessonEntity)

    @Delete
    suspend fun deleteLesson(lessonEntity: LessonEntity)

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
    suspend fun insertListOfLessonTypes(list: List<LessonTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfLessons(list: List<LessonEntity>)

}