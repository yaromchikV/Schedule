package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "SELECT lessons.id, lessons.subject, lesson_types.id AS type_id, lesson_types.type AS type, lessons.note, start_time, end_time, " +
            "days.id AS day_id, days.name AS day_name, lessons.week_number, lessons.subgroup, teachers.id AS teacher_id, " +
            "(teachers.surname || ' ' || substr(teachers.name, 1, 1) || '. ' || substr(teachers.patronymic, 1, 1) || '.') AS teacher, " +
            "classrooms.id AS classroom_id, (classrooms.number || '-' || buildings.name) AS classroom, " +
            "groups.id AS group_id " +
            "FROM lessons " +
            "JOIN groups ON groups.id = group_id " +
            "JOIN teachers ON teachers.id = teacher_id " +
            "JOIN classrooms ON classrooms.id = classroom_id " +
            "JOIN buildings ON buildings.id = classrooms.building_id " +
            "JOIN lesson_types ON lesson_types.id = type_id " +
            "JOIN days ON days.id = day_of_week_id"
)
data class LessonView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "type_id") val typeId: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "day_id") val dayOfWeekId: Int,
    @ColumnInfo(name = "day_name") val dayOfWeek: String,
    @ColumnInfo(name = "week_number") val weeks: String,
    @ColumnInfo(name = "subgroup") val subgroup: Int,
    @ColumnInfo(name = "teacher_id") val teacherId: Int?,
    @ColumnInfo(name = "teacher") val teacher: String?,
    @ColumnInfo(name = "classroom_id") val classroomId: Int?,
    @ColumnInfo(name = "classroom") val classroom: String?,
    @ColumnInfo(name = "group_id") val groupId: Int
)