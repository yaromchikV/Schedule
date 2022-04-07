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
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.data.models.views.LessonView
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM LessonView")
    fun getLessons(): Flow<List<LessonView>>

    @Query("SELECT * FROM days")
    fun getDaysOfWeek(): Flow<List<DayOfWeekEntity>>

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