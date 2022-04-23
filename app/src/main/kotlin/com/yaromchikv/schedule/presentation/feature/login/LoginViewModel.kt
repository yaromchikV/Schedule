package com.yaromchikv.schedule.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.schedule.presentation.common.AccessRights
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val repository: ScheduleRepository,
    private val getAccessPermissionUseCase: GetAccessPermissionUseCase
) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val _accessState = MutableStateFlow<AccessState>(AccessState.Idle)
    val accessState: StateFlow<AccessState> = _accessState

    private var getIdJob: Job? = null
    private var getRoleJob: Job? = null

    fun getAccessPermission() {
        _accessState.value = AccessState.Loading
        getIdJob?.cancel()
        getIdJob = repository.getIdByUsername(username)
            .onEach { id ->
                if (id != null) {
                    getRoleJob?.cancel()
                    getRoleJob = getAccessPermissionUseCase(id, username, password)
                        .onEach { access ->
                            _accessState.value = when (access) {
                                0 -> AccessState.Granted(AccessRights.USER)
                                1 -> AccessState.Granted(AccessRights.ADMIN)
                                else -> AccessState.Denied
                            }
                        }
                        .launchIn(viewModelScope)
                } else {
                    _accessState.value = AccessState.Denied
                }
            }
            .launchIn(viewModelScope)
    }

    sealed class AccessState {
        object Idle : AccessState()
        object Loading: AccessState()
        data class Granted(val accessRights: AccessRights) : AccessState()
        object Denied : AccessState()
    }
}