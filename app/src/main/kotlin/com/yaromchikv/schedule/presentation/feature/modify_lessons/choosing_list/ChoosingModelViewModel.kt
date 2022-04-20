package com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.BaseModel
import com.yaromchikv.domain.usecase.GetClassroomsUseCase
import com.yaromchikv.domain.usecase.GetDaysOfWeekUseCase
import com.yaromchikv.domain.usecase.GetLessonTypesUseCase
import com.yaromchikv.domain.usecase.GetTeachersUseCase
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ListMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingModelViewModel(
    private val listMode: ListMode,
    private val getClassroomsUseCase: GetClassroomsUseCase,
    private val getDaysOfWeekUseCase: GetDaysOfWeekUseCase,
    private val getLessonTypesUseCase: GetLessonTypesUseCase,
    private val getTeachersUseCase: GetTeachersUseCase
) : ViewModel() {

    private val _models = MutableStateFlow<List<BaseModel?>>(emptyList())
    val models: StateFlow<List<BaseModel?>> = _models

    private var getModelsJob: Job? = null

    init {
        getModels()
    }

    private fun getModels() {
        val flow = when (listMode) {
            ListMode.CLASSROOMS -> getClassroomsUseCase()
            ListMode.DAYS_OF_WEEK -> getDaysOfWeekUseCase()
            ListMode.LESSON_TYPES -> getLessonTypesUseCase()
            ListMode.TEACHERS -> getTeachersUseCase()
        }

        getModelsJob?.cancel()
        getModelsJob = flow
            .onEach { models -> _models.value = models }
            .launchIn(viewModelScope)
    }
}