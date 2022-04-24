package com.yaromchikv.data.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaromchikv.domain.model.schedule.TeacherInterface

@JsonClass(generateAdapter = true)
data class TeacherDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "lastName") override val surname: String,
    @Json(name = "firstName") override val name: String,
    @Json(name = "middleName") override val patronymic: String,
    @Json(name = "rank") override val rank: String?
) : TeacherInterface