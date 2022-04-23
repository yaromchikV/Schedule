package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaromchikv.domain.model.schedule.DayScheduleInterface

@JsonClass(generateAdapter = true)
data class DayScheduleDto(
    @Json(name = "weekDay") override val weekDay: String,
    @Json(name = "schedule") override val listOfLessons: List<LessonDto>
): DayScheduleInterface