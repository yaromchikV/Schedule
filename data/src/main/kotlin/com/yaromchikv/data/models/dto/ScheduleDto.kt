package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleDto(
    @Json(name = "studentGroup") val studentGroup: GroupDto?,
    @Json(name = "schedules") val listOfDaySchedules: List<DayScheduleDto>,
)