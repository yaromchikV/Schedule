package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LessonDto(
    @Json(name = "auditory") val auditory: List<String>,
    @Json(name = "weekNumber") val weekNumber: List<Any>,
    @Json(name = "startLessonTime") val startLessonTime: String,
    @Json(name = "endLessonTime") val endLessonTime: String,
    @Json(name = "lessonType") val lessonType: String,
    @Json(name = "subject") val subject: String,
    @Json(name = "subjectFullName") val subjectFullName: String,
    @Json(name = "note") val note: String?,
    @Json(name = "employee") val employee: List<EmployeeDto>,
    @Json(name = "studentGroup") val studentGroup: List<String>,
    @Json(name = "numSubgroup") val numSubgroup: Int
)