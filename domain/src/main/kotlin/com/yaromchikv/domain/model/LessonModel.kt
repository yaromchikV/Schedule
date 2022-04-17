package com.yaromchikv.domain.model

data class LessonModel(
    val id: Int,
    val subject: String,
    val typeId: Int,
    val type: String,
    val note: String?,
    val startTime: String,
    val endTime: String,
    val dayOfWeekId: Int,
    val dayOfWeek: String,
    val weeks: List<Int>?,
    val subgroup: Int,
    val teacherId: Int?,
    val teacher: String?,
    val classroomId: Int?,
    val classroom: String?,
    val groupId: Int
)