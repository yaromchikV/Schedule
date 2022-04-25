package com.yaromchikv.data.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaromchikv.domain.model.GroupInterface

@JsonClass(generateAdapter = true)
data class GroupDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "specialityDepartmentEducationFormId") override val specialityId: Int,
) : GroupInterface