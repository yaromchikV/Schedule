package com.yaromchikv.schedule.presentation.feature.modify_lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.usecase.AddLessonUseCase
import com.yaromchikv.domain.usecase.DeleteLessonUseCase
import com.yaromchikv.domain.usecase.GetLessonByIdUseCase
import com.yaromchikv.domain.usecase.UpdateLessonUseCase
import com.yaromchikv.schedule.presentation.common.NULL_ID
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ModifyLessonViewModel(
    private val getLessonByIdUseCase: GetLessonByIdUseCase,
    private val addLessonUseCase: AddLessonUseCase,
    private val updateLessonUseCase: UpdateLessonUseCase,
    private val deleteLessonUseCase: DeleteLessonUseCase
) : ViewModel() {

    private var lessonId: Int = NULL_ID

    private val _lesson = MutableStateFlow<LessonModel?>(LessonModel())
    val lesson: StateFlow<LessonModel?> = _lesson

    private val _applySuccessfully = MutableSharedFlow<Boolean>()
    val applySuccessfully: SharedFlow<Boolean> = _applySuccessfully

    private var getLessonJob: Job? = null

    private fun getLesson() {
        getLessonJob?.cancel()
        getLessonJob = getLessonByIdUseCase(lessonId)
            .onEach { lesson -> _lesson.value = lesson }
            .launchIn(viewModelScope)
    }

    fun updateLesson(lessonModel: LessonModel) {
        _lesson.value = lessonModel
    }

    fun applyChangesClick(lessonModel: LessonModel) {
        viewModelScope.launch {
            val isCorrect =
                lessonModel.subject.isNotBlank() && lessonModel.startTime.isNotBlank() && lessonModel.endTime.isNotBlank() && lessonModel.typeId != null && lessonModel.classroomId != null && lessonModel.teacherId != null && lessonModel.dayOfWeekId != null && lessonModel.weeks.isNotEmpty()
            if (isCorrect) {
                if (lessonId == NULL_ID) {
                    addLessonUseCase(lessonModel)
                } else {
                    updateLessonUseCase(lessonModel)
                }
            }
            _applySuccessfully.emit(isCorrect)
        }
    }

    fun deleteButtonClick() {
        viewModelScope.launch {
            _lesson.value?.let { deleteLessonUseCase(it) }
        }
    }

    fun setLessonId(id: Int) {
        lessonId = id
        getLesson()
    }

    fun clearLesson() {
        lessonId = NULL_ID
        _lesson.value = LessonModel()
    }

    fun setClassroom(classroom: ClassroomModel?) {
        _lesson.value = _lesson.value?.copy(
            classroomId = classroom?.id,
            classroom = if (classroom != null) "${classroom.number}-${classroom.buildingName}" else null
        )
    }

    fun setDayOfWeek(dayOfWeek: DayOfWeekModel?) {
        _lesson.value = _lesson.value?.copy(
            dayOfWeekId = dayOfWeek?.id,
            dayOfWeek = dayOfWeek?.name
        )
    }

    fun setLessonType(lessonType: LessonTypeModel?) {
        _lesson.value = _lesson.value?.copy(
            typeId = lessonType?.id,
            type = lessonType?.type
        )
    }

    fun setTeacher(teacher: TeacherModel?) {
        _lesson.value = _lesson.value?.copy(
            teacherId = teacher?.id,
            teacher = if (teacher != null) {
                "${teacher.surname} ${teacher.name[0]}. ${teacher.patronymic[0]}."
            } else null
        )
    }
}