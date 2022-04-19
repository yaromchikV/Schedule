package com.yaromchikv.schedule.presentation.feature.editing.choosing_lesson_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.usecase.GetLessonTypesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingLessonTypeViewModel(
    private val getLessonTypesUseCase: GetLessonTypesUseCase
) : ViewModel() {

    private val _lessonTypes = MutableStateFlow<List<LessonTypeModel?>>(emptyList())
    val lessonTypes: StateFlow<List<LessonTypeModel?>> = _lessonTypes

    private var getLessonTypesJob: Job? = null

    init {
        getLessonTypes()
    }

    private fun getLessonTypes() {
        getLessonTypesJob?.cancel()
        getLessonTypesJob = getLessonTypesUseCase()
            .onEach { lessonTypes -> _lessonTypes.value = lessonTypes }
            .launchIn(viewModelScope)
    }
}