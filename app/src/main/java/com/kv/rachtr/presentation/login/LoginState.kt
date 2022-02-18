package com.kv.rachtr.presentation.login

import com.kv.rachtr.domain.model.UserModel

sealed class LoginState {
    data class Success(val message: String, val data: UserModel) : LoginState()
    data class Error(val message: String) : LoginState()
    object Loading : LoginState()
}