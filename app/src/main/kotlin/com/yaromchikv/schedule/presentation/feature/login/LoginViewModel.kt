package com.yaromchikv.schedule.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.domain.usecase.GetIdByUsernameUseCase
import com.yaromchikv.schedule.presentation.common.AccessRights
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val getIdByUsernameUseCase: GetIdByUsernameUseCase,
    private val getAccessPermissionUseCase: GetAccessPermissionUseCase
) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val _access = MutableStateFlow<AccessState>(AccessState.Idle)
    val access: StateFlow<AccessState> = _access

    private var getIdJob: Job? = null
    private var getRoleJob: Job? = null

    fun getAccessPermission() {
        getIdJob?.cancel()
        getIdJob = getIdByUsernameUseCase(username)
            .onEach { id ->
                if (id != null) {
                    getRoleJob?.cancel()
                    getRoleJob = getAccessPermissionUseCase(id, username, password)
                        .onEach { access ->
                            _access.value = when (access) {
                                0 -> AccessState.Granted(AccessRights.USER)
                                1 -> AccessState.Granted(AccessRights.ADMIN)
                                else -> AccessState.Denied
                            }
                        }
                        .launchIn(viewModelScope)
                } else {
                    _access.value = AccessState.Denied
                }
            }
            .launchIn(viewModelScope)
    }

    sealed class AccessState {
        object Idle : AccessState()
        data class Granted(val accessRights: AccessRights) : AccessState()
        object Denied : AccessState()
    }
}