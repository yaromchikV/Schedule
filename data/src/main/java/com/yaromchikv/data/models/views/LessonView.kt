package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.yaromchikv.domain.model.LessonInterface

@DatabaseView(
    "SELECT lessons.id, subject, type, note, start_time, end_time, week_number, subgroup_num, " +
            "(teachers.last_name || ' ' || substr(teachers.first_name, 1, 1) || '. ' || substr(teachers.middle_name, 1, 1) || '.') as teacher, (classrooms.name + '-' + buildings.name) as classroom_name " +
            "FROM lessons " +
            "JOIN groups ON groups.id = group_id " +
            "JOIN teachers ON teachers.id = teacher_id " +
            "JOIN classrooms ON classrooms.id = classroom_id " +
            "JOIN buildings ON buildings.id = classrooms.building_id " +
            "JOIN days ON days.id = day_of_week_id"
)
data class LessonView(
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "subject") override val subject: String,
    @ColumnInfo(name = "type") override val type: String,
    @ColumnInfo(name = "note") override val note: String?,
    @ColumnInfo(name = "start_time") override val startTime: String,
    @ColumnInfo(name = "end_time") override val endTime: String,
    @ColumnInfo(name = "week_number") override val weeks: String,
    @ColumnInfo(name = "subgroup_num") override val subgroupNum: String?,
    @ColumnInfo(name = "teacher") override val teacher: String,
    @ColumnInfo(name = "classroom_name") override val classroomName: String
): LessonInterface
