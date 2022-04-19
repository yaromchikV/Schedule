package com.yaromchikv.schedule.presentation.feature.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
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

class EditLessonViewModel(
    private val getLessonByIdUseCase: GetLessonByIdUseCase,
    private val updateLessonUseCase: UpdateLessonUseCase,
    private val deleteLessonUseCase: DeleteLessonUseCase
) : ViewModel() {

    private var lessonId: Int = NULL_ID

    private val _lesson = MutableStateFlow<LessonModel?>(null)
    val lesson: StateFlow<LessonModel?> = _lesson

    private var getLessonJob: Job? = null

    private val _updateSuccessfully = MutableSharedFlow<Boolean>()
    val updateSuccessfully: SharedFlow<Boolean> = _updateSuccessfully

    init {
        getLesson()
    }

    private fun getLesson() {
        getLessonJob?.cancel()
        getLessonJob = getLessonByIdUseCase(lessonId)
            .onEach { lesson -> _lesson.value = lesson }
            .launchIn(viewModelScope)
    }

    fun applyChangesClick(lessonModel: LessonModel) {
        viewModelScope.launch {
            if (lessonModel.subject.isBlank() || lessonModel.typeId == null || lessonModel.classroomId == null
                || lessonModel.teacherId == null || lessonModel.dayOfWeekId == null || lessonModel.weeks.isEmpty()
            ) {
                _updateSuccessfully.emit(false)
            } else {
                updateLessonUseCase(lessonModel)
                _updateSuccessfully.emit(true)
            }
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

    fun setClassroom(classroom: ClassroomModel?) {
        val classroomName =
            if (classroom != null) "${classroom.number}-${classroom.buildingName}" else null

        _lesson.value = _lesson.value?.copy(
            classroomId = classroom?.id,
            classroom = classroomName
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
        val fullName = if (teacher != null) {
            "${teacher.surname} ${teacher.name[0]}. ${teacher.patronymic[0]}."
        } else null

        _lesson.value = _lesson.value?.copy(
            teacherId = teacher?.id,
            teacher = fullName
        )
    }
}