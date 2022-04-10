package com.yaromchikv.schedule.presentation.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.usecase.GetListOfDaysOfWeekUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ScheduleViewModel(
    private val getListOfDaysOfWeekUseCase: GetListOfDaysOfWeekUseCase
) : ViewModel() {

    private val _selectedGroup = MutableStateFlow<GroupModel?>(null)
    val selectedGroup: StateFlow<GroupModel?> = _selectedGroup

    private val _daysOfWeek = MutableStateFlow<List<DayOfWeekInterface>>(emptyList())
    val daysOfWeek: StateFlow<List<DayOfWeekInterface>> = _daysOfWeek

    private var getDaysOfWeek: Job? = null

    init {
        _selectedGroup.value = GroupModel(1, "910903", "ИПОИТ")

        getDaysOfWeek()
    }

    private fun getDaysOfWeek() {
        getDaysOfWeek?.cancel()
        getDaysOfWeek = getListOfDaysOfWeekUseCase()
            .onEach { days -> _daysOfWeek.value = days }
            .launchIn(viewModelScope)
    }
}
