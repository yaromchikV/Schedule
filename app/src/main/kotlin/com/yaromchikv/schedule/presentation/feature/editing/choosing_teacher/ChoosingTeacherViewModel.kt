package com.yaromchikv.schedule.presentation.feature.editing.choosing_teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.usecase.GetTeachersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingTeacherViewModel(
    private val getTeachersUseCase: GetTeachersUseCase
) : ViewModel() {

    private val _teachers = MutableStateFlow<List<TeacherModel?>>(emptyList())
    val teachers: StateFlow<List<TeacherModel?>> = _teachers

    private var getTeachersJob: Job? = null

    init {
        getTeachers()
    }

    private fun getTeachers() {
        getTeachersJob?.cancel()
        getTeachersJob = getTeachersUseCase()
            .onEach { teachers -> _teachers.value = teachers }
            .launchIn(viewModelScope)
    }
}