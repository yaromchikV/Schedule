package com.yaromchikv.data.models.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuildingDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)