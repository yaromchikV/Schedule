package com.yaromchikv.domain.model

data class LessonTypeModel(
    override val id: Int? = null,
    val type: String
) : BaseModel()