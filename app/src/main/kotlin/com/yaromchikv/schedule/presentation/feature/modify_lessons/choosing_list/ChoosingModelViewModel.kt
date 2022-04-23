package com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.BaseModel
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ListMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingModelViewModel(
    private val listMode: ListMode,
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _modelsState = MutableStateFlow<UiState>(UiState.Idle)
    val modelsState: StateFlow<UiState> = _modelsState

    private var getModelsJob: Job? = null

    init {
        getModels()
    }

    private fun getModels() {
        _modelsState.value = UiState.Loading
        val flow = when (listMode) {
            ListMode.CLASSROOMS -> repository.getClassrooms()
            ListMode.DAYS_OF_WEEK -> repository.getDaysOfWeek()
            ListMode.LESSON_TYPES -> repository.getLessonTypes()
            ListMode.TEACHERS -> repository.getTeachers()
        }

        getModelsJob?.cancel()
        getModelsJob = flow
            .onEach { models -> _modelsState.value = UiState.Ready(models) }
            .launchIn(viewModelScope)
    }

    sealed class UiState {
        object Idle: UiState()
        data class Ready(val data: List<BaseModel?>): UiState()
        object Loading: UiState()
    }
}