package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import com.yaromchikv.domain.model.LessonInterface

data class LessonView(
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "subject") override val subject: String,
    @ColumnInfo(name = "type") override val type: String,
    @ColumnInfo(name = "note") override val note: String?,
    @ColumnInfo(name = "start_time") override val startTime: String,
    @ColumnInfo(name = "end_time") override val endTime: String,
    @ColumnInfo(name = "week_number") override val weeks: String?,
    @ColumnInfo(name = "subgroup") override val subgroup: Int,
    @ColumnInfo(name = "teacher") override val teacher: String?,
    @ColumnInfo(name = "classroom_name") override val classroom: String
): LessonInterface
