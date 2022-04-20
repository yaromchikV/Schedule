package com.yaromchikv.schedule.presentation.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.usecase.GetDaysOfWeekUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getDaysOfWeekUseCase: GetDaysOfWeekUseCase
) : ViewModel() {

    private val _daysOfWeek = MutableStateFlow<List<DayOfWeekModel>>(emptyList())
    val daysOfWeek: StateFlow<List<DayOfWeekModel>> = _daysOfWeek

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events

    private var getDaysOfWeekJob: Job? = null

    init {
        getDaysOfWeek()
    }

    private fun getDaysOfWeek() {
        getDaysOfWeekJob?.cancel()
        getDaysOfWeekJob = getDaysOfWeekUseCase()
            .onEach { days -> _daysOfWeek.value = days }
            .launchIn(viewModelScope)
    }

    fun changeGroupClick() {
        viewModelScope.launch {
            _events.emit(Event.ChangeGroup)
        }
    }

    fun nextFragmentClick(current: Int?) {
        viewModelScope.launch {
            _events.emit(Event.ChangeFragment(if (current != null) current + 1 else 0))
        }
    }

    fun prevFragmentClick(current: Int?) {
        viewModelScope.launch {
            _events.emit(Event.ChangeFragment(if (current != null) current - 1 else 0))
        }
    }

    fun editLessonClick(lessonId: Int) {
        viewModelScope.launch {
            _events.emit(Event.SelectLesson(lessonId))
        }
    }

    fun addLessonClick() {
        viewModelScope.launch {
            _events.emit(Event.AddLesson)
        }
    }

    sealed class Event {
        object ChangeGroup : Event()
        data class ChangeFragment(val index: Int) : Event()
        data class SelectLesson(val lessonId: Int) : Event()
        object AddLesson : Event()
    }
}
