package com.yaromchikv.schedule.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.usecase.GetListOfGroupsUseCase
import com.yaromchikv.schedule.presentation.common.AccessRights
import com.yaromchikv.schedule.presentation.common.GROUP_ID_PREFS_KEY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences: SharedPreferences,
    private val getListOfGroupsUseCase: GetListOfGroupsUseCase
) : ViewModel() {

    private val _groups = MutableStateFlow<List<GroupModel>?>(null)
    val groups: StateFlow<List<GroupModel>?> = _groups

    private val _selectedGroup = MutableStateFlow<GroupModel?>(null)
    val selectedGroup: StateFlow<GroupModel?> = _selectedGroup

    var accessRights = AccessRights.NONE

    private var getGroupsJob: Job? = null

    init {
        getGroups()
    }

    private fun getGroups() {
        getGroupsJob?.cancel()
        getGroupsJob = getListOfGroupsUseCase()
            .onEach { groups ->
                _groups.value = groups
                updateSelectedGroup()
            }
            .launchIn(viewModelScope)
    }

    private fun updateSelectedGroup() {
        _selectedGroup.value = _groups.value?.find {
            it.id == preferences.getInt(GROUP_ID_PREFS_KEY, 1)
        }?.apply {
            isSelected = true
        }
    }


    fun selectGroupClick(group: GroupModel) {
        viewModelScope.launch {
            _selectedGroup.value = group
            _groups.value?.map { it.isSelected = it.id == group.id }

            preferences.edit().putInt(GROUP_ID_PREFS_KEY, _selectedGroup.value?.id ?: 1).apply()
        }
    }
}