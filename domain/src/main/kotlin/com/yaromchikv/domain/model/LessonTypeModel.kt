package com.yaromchikv.domain.model

data class LessonTypeModel(
    override val id: Int,
    val type: String
) : BaseModel()