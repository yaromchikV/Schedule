package com.yaromchikv.schedule.presentation.feature.change_group_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.usecase.GetListOfGroupsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChangeGroupViewModel: ViewModel() {}