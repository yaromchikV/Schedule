package com.yaromchikv.data.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeDto(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "middleName") val middleName: String,
    @Json(name = "fio") val fio: String,
    @Json(name = "rank") val rank: String?,
    @Json(name = "photoLink") val photoLink: String,
)