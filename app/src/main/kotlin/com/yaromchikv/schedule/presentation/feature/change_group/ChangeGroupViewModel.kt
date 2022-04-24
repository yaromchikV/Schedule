package com.yaromchikv.schedule.presentation.feature.change_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.common.Result
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.domain.usecase.AddScheduleUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class ChangeGroupViewModel(
    private val repository: ScheduleRepository,
    private val addScheduleUseCase: AddScheduleUseCase
) : ViewModel() {

    private val _addGroupState = MutableStateFlow<UiState>(UiState.Idle)
    val addGroupState: StateFlow<UiState> = _addGroupState

    private var addScheduleJob: Job? = null

    fun addGroupClick(groupName: String) {
        viewModelScope.launch {
            _addGroupState.value = UiState.Loading
            when (val response = repository.getScheduleFromApi(groupName)) {
                is Result.Success -> {
                    addScheduleJob?.cancel()
                    addScheduleJob = combine(
                        repository.getDaysOfWeek(),
                        repository.getLessonTypes(),
                        repository.getSpecialities(),
                        repository.getClassrooms()
                    ) { days, lessonTypes, specialities, classrooms ->
                        response.data?.let { schedule ->
                            addScheduleUseCase(
                                groupName,
                                schedule,
                                days,
                                lessonTypes,
                                specialities,
                                classrooms
                            )
                            _addGroupState.value = UiState.Ready
                        }
                    }.launchIn(viewModelScope)
                }
                is Result.Error -> {
                    _addGroupState.value = UiState.Error(response.message)
                }
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Ready : UiState()
        object Loading : UiState()
        data class Error(val message: String?) : UiState()
    }
}