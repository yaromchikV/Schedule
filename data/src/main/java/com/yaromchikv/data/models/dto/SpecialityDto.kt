package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpecialityDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "abbrev") val abbrev: String,
    @Json(name = "facultyId") val facultyId: Int,
)