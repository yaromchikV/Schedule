package com.yaromchikv.domain.model

data class ClassroomModel(
    override val id: Int? = null,
    val number: String,
    val buildingId: Int,
    val buildingName: String
) : BaseModel()