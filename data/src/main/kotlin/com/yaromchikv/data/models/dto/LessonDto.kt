package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaromchikv.domain.model.schedule.LessonInterface

@JsonClass(generateAdapter = true)
data class LessonDto(
    @Json(name = "auditory") override val classroom: List<String>,
    @Json(name = "weekNumber") override val weeks: List<Int>,
    @Json(name = "startLessonTime") override val startTime: String,
    @Json(name = "endLessonTime") override val endTime: String,
    @Json(name = "lessonType") override val lessonType: String,
    @Json(name = "subject") override val subject: String,
    @Json(name = "note") override val note: String?,
    @Json(name = "employee") override val teacher: List<TeacherDto>,
    @Json(name = "numSubgroup") override val subgroup: Int
) : LessonInterface