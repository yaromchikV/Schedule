package com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.usecase.GetDaysOfWeekUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChoosingDayOfWeekViewModel(
    private val getDaysOfWeekUseCase: GetDaysOfWeekUseCase
) : ViewModel() {

    private val _daysOfWeek = MutableStateFlow<List<DayOfWeekModel?>>(emptyList())
    val daysOfWeek: StateFlow<List<DayOfWeekModel?>> = _daysOfWeek

    private var getDaysOfWeekJob: Job? = null

    init {
        getDaysOfWeek()
    }

    private fun getDaysOfWeek() {
        getDaysOfWeekJob?.cancel()
        getDaysOfWeekJob = getDaysOfWeekUseCase()
            .onEach { daysOfWeek -> _daysOfWeek.value = daysOfWeek }
            .launchIn(viewModelScope)
    }
}