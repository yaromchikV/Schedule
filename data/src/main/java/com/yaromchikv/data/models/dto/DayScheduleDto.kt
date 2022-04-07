package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayScheduleDto(
    @Json(name = "weekDay") val weekDay: String,
    @Json(name = "schedule") val listOfLessons: List<LessonDto>
)