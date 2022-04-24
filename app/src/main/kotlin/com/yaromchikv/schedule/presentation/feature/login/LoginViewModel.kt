package com.yaromchikv.schedule.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.schedule.presentation.common.AccessRights
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: ScheduleRepository,
    private val getAccessPermissionUseCase: GetAccessPermissionUseCase
) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val _accessState = MutableStateFlow<AccessState>(AccessState.Idle)
    val accessState: StateFlow<AccessState> = _accessState

    fun getAccessPermission() {
        viewModelScope.launch {
            _accessState.value = AccessState.Loading
            val id = repository.getIdByUsername(username)
            if (id != null) {
                val access = getAccessPermissionUseCase(id, username, password)
                _accessState.value = when (access) {
                    0 -> AccessState.Granted(AccessRights.USER)
                    1 -> AccessState.Granted(AccessRights.ADMIN)
                    else -> AccessState.Denied
                }
            } else {
                _accessState.value = AccessState.Denied
            }
        }
    }

    sealed class AccessState {
        object Idle : AccessState()
        object Loading : AccessState()
        data class Granted(val accessRights: AccessRights) : AccessState()
        object Denied : AccessState()
    }
}