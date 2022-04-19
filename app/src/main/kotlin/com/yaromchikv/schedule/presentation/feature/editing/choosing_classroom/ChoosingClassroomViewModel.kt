package com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.domain.usecase.GetClassroomsUseCase
import com.yaromchikv.domain.usecase.GetDaysOfWeekUseCase
import com.yaromchikv.domain.usecase.GetLessonTypesUseCase
import com.yaromchikv.domain.usecase.GetTeachersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingClassroomViewModel(
    private val getClassroomsUseCase: GetClassroomsUseCase
) : ViewModel() {

    private val _classrooms = MutableStateFlow<List<ClassroomModel?>>(emptyList())
    val classrooms: StateFlow<List<ClassroomModel?>> = _classrooms

    private var getClassroomsJob: Job? = null

    init {
        getClassrooms()
    }

    private fun getClassrooms() {
        getClassroomsJob?.cancel()
        getClassroomsJob = getClassroomsUseCase()
            .onEach { classrooms -> _classrooms.value = classrooms }
            .launchIn(viewModelScope)
    }
}