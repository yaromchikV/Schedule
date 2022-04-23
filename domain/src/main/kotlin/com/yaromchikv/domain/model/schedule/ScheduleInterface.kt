package com.yaromchikv.domain.model.schedule

interface ScheduleInterface {
    val group: GroupInterface
    val lisOfDays: List<DayScheduleInterface>
}