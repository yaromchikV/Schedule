package com.yaromchikv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
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

@Database(
    entities = [
        RoleEntity::class,
        UserEntity::class,
        BuildingEntity::class,
        ClassroomEntity::class,
        DayOfWeekEntity::class,
        TeacherEntity::class,
        FacultyEntity::class,
        SpecialityEntity::class,
        GroupEntity::class,
        LessonEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract val scheduleDao: ScheduleDao

    companion object {
        const val DATABASE_NAME = "schedule"
    }
}
