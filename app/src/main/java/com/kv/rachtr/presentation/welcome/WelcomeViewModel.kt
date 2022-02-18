package com.kv.rachtr.presentation.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kv.rachtr.data.pref.DataStoreManager
import com.kv.rachtr.domain.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeViewModel(dataStoreManager: DataStoreManager) : ViewModel() {
    var user = MutableLiveData<UserModel>()

    init {
        viewModelScope.launch {
            dataStoreManager.getUser().collect {
                    withContext(Dispatchers.Main) {
                        user.value = it
                    }
            }
        }
    }
}

class WelcomeViewModelFactory(val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}