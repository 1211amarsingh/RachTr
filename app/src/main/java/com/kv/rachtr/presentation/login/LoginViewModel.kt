package com.kv.rachtr.presentation.login

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kv.rachtr.domain.model.UserModel
import com.kv.rachtr.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(var loginRepository: LoginRepository) : ViewModel() {

    val user_name = MutableLiveData("")
    val password = MutableLiveData("")
    val u_type = MutableLiveData("Executive")
    val screenState = MutableLiveData<LoginState>()

    fun onLogin(view: View) {
        if (screenState.value is LoginState.Success) return
        screenState.value = LoginState.Loading
        val u_name = user_name.value.toString()
        val password_txt = password.value.toString()
        val u_type_txt = u_type.value.toString()

        if (TextUtils.isEmpty(u_name)) {
            screenState.value = LoginState.Error("Please Enter User Name")
        } else if (TextUtils.isEmpty(password_txt)) {
            screenState.value = LoginState.Error("Please Enter Password")
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                Log.e("LoginR", "1");
                val response = loginRepository.login(u_name, password_txt, u_type_txt)

                withContext(Dispatchers.Main) {
                    if (response != null) {
                        val body = response.body()
                        if (response.isSuccessful && response.code() == 200) {
                            if (body != null) {
                                screenState.value =
                                    LoginState.Success(
                                        "Login Successful!",
                                        body
                                    )
                            }
                        } else {
                            Log.e(
                                "LoginR",
                                "3 " + response.message() + "\n  " + Gson().toJson(response.errorBody()) + "\n1" + response.body()
                            )
                            screenState.value =
                                LoginState.Error(response.message())
                        }
                    }
                }
            }

        }
    }
}


class LoginViewModelFactory(var loginRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
