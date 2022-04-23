package com.yaromchikv.domain.model.schedule

interface DayScheduleInterface {
    val weekDay: String
    val listOfLessons: List<LessonInterface>
}