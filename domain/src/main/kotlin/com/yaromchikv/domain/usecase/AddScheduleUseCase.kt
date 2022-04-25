package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.SpecialityModel
import com.yaromchikv.domain.model.ScheduleInterface
import com.yaromchikv.domain.repository.ScheduleRepository

class AddScheduleUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(
        groupName: String,
        schedule: ScheduleInterface,
        days: List<DayOfWeekModel>,
        lessonTypes: List<LessonTypeModel>,
        specialities: List<SpecialityModel>,
        classrooms: List<ClassroomModel>
    ) {
        val lessonsList = mutableListOf<LessonModel>()

        schedule.lisOfDays.forEach { day ->
            val dayOfWeek = day.weekDay
            val dayOfWeekId = days.find { it.name == day.weekDay }?.id
            day.listOfLessons.forEach { lesson ->
                val classroomId: Int?
                if (lesson.classroom.isNotEmpty()) {
                    val classroomName = lesson.classroom[0]
                    var classroomNumber = ""
                    var buildingId: Int? = null
                    for (i in classroomName.indices) {
                        if (classroomName[i] != '-') {
                            classroomNumber += classroomName[i]
                        } else {
                            buildingId = classroomName[i + 1].digitToIntOrNull()
                            break
                        }
                    }
                    classroomId = classrooms.find {
                        it.number == classroomNumber && it.buildingId == buildingId
                    }?.id
                } else classroomId = null

                lessonsList.add(
                    LessonModel(
                        subject = lesson.subject,
                        typeId = lessonTypes.find { it.type == lesson.lessonType }?.id,
                        type = lesson.lessonType,
                        note = lesson.note,
                        startTime = lesson.startTime,
                        endTime = lesson.endTime,
                        dayOfWeekId = dayOfWeekId,
                        dayOfWeek = dayOfWeek,
                        weeks = lesson.weeks,
                        subgroup = lesson.subgroup,
                        teacherId = if (lesson.teacher.isNotEmpty()) lesson.teacher[0].id else null,
                        classroomId = classroomId,
                        groupId = schedule.group.id
                    )
                )
            }
        }
        val groupModel = GroupModel(
            id = schedule.group.id,
            name = groupName,
            specialityId = schedule.group.specialityId,
            speciality = specialities.find { it.id == schedule.group.specialityId }?.name ?: "",
        )

        val isExist = repository.checkIfGroupExist(groupName)
        if (!isExist) {
            repository.addGroup(groupModel)
        } else {
            repository.deleteLessonsByGroupId(groupModel.id)
        }
        repository.addLessonList(lessonsList)
    }
}