package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo

data class LessonView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "week_number") val weeks: String?,
    @ColumnInfo(name = "subgroup") val subgroup: Int,
    @ColumnInfo(name = "teacher") val teacher: String?,
    @ColumnInfo(name = "classroom_name") val classroom: String
)
