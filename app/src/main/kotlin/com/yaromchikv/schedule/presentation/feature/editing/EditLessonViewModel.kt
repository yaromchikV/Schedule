package com.yaromchikv.schedule.presentation.feature.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.usecase.DeleteLessonUseCase
import com.yaromchikv.domain.usecase.GetLessonByIdUseCase
import com.yaromchikv.domain.usecase.UpdateLessonUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EditLessonViewModel(
    private val lessonId: Int,
    private val getLessonByIdUseCase: GetLessonByIdUseCase,
    private val updateLessonUseCase: UpdateLessonUseCase,
    private val deleteLessonUseCase: DeleteLessonUseCase
) : ViewModel() {

    private val _lesson = MutableStateFlow<LessonModel?>(null)
    val lesson: StateFlow<LessonModel?> = _lesson

    private var getLessonJob: Job? = null

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
            updateLessonUseCase(lessonModel)
        }
    }

    fun deleteButtonClick() {
        viewModelScope.launch {
            _lesson.value?.let { deleteLessonUseCase(it) }
        }
    }
}