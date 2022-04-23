package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaromchikv.domain.model.schedule.GroupInterface
import com.yaromchikv.domain.model.schedule.ScheduleInterface

@JsonClass(generateAdapter = true)
data class ScheduleDto(
    @Json(name = "studentGroup") override val group: GroupDto,
    @Json(name = "schedules") override val lisOfDays: List<DayScheduleDto>
) : ScheduleInterface