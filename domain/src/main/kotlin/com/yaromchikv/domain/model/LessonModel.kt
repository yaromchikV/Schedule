package com.yaromchikv.domain.model

data class LessonModel(
    val id: Int,
    val subject: String,
    val type: String,
    val note: String?,
    val startTime: String,
    val endTime: String,
    val weeks: List<Int>?,
    val subgroup: Int,
    val teacher: String?,
    val classroom: String
)