package com.yaromchikv.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = DayOfWeekEntity::class,
            parentColumns = ["id"],
            childColumns = ["day_of_week_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClassroomEntity::class,
            parentColumns = ["id"],
            childColumns = ["classroom_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["group_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TeacherEntity::class,
            parentColumns = ["id"],
            childColumns = ["teacher_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "day_of_week_id") val dayOfWeekId: Int,
    @ColumnInfo(name = "classroom_id") val classroomId: Int,
    @ColumnInfo(name = "week_number") val weekNumbers: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "group_id") val groupId: Int,
    @ColumnInfo(name = "subgroup_num") val numSubgroup: String?,
    @ColumnInfo(name = "teacher_id") val teacherId: Int
)