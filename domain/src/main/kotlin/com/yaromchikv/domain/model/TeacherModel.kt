package com.yaromchikv.domain.model

data class TeacherModel(
    override val id: Int,
    val surname: String,
    val name: String,
    val patronymic: String,
    val rank: String
) : BaseModel()