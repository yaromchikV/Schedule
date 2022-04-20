package com.yaromchikv.domain.model

data class LessonModel(
    val id: Int? = null,
    val subject: String = "",
    val typeId: Int? = null,
    val type: String? = null,
    val note: String? = null,
    val startTime: String = "",
    val endTime: String = "",
    val dayOfWeekId: Int? = null,
    val dayOfWeek: String? = null,
    val weeks: List<Int> = emptyList(),
    val subgroup: Int = 0,
    val teacherId: Int? = null,
    val teacher: String? = null,
    val classroomId: Int? = null,
    val classroom: String? = null,
    val groupId: Int? = null
)