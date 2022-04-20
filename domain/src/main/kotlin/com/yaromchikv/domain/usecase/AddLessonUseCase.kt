package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository

class AddLessonUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(lessonModel: LessonModel) {
        repository.addLesson(lessonModel)
    }
}