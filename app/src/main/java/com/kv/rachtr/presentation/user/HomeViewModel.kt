package com.kv.rachtr.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kv.rachtr.data.pref.DataStoreManager
import com.kv.rachtr.domain.model.TodoModel
import com.kv.rachtr.domain.model.UserModel
import com.kv.rachtr.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val apiRepository: HomeRepository, val dataStoreManager: DataStoreManager) :
    ViewModel() {
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

    fun getTodoData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiRepository.getTodo()

            withContext(Dispatchers.Main) {
                if (response != null) {
                    val body = response.body()
                    if (response.isSuccessful && response.code() == 200) {
                        if (body != null) {
                            val list: ArrayList<TodoModel> = ArrayList()
                            list.clear()
                            for (i in 0..19) {
                                if (body.size > i)
                                    list.add(body.get(i))
                            }
                            tododata.value = list
                        }
                    }
                }
            }
        }
    }

    var tododata = MutableLiveData<ArrayList<TodoModel>>()

}

class HomeViewModelFactory(
    val apiRepository: HomeRepository,
    val dataStoreManager: DataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(apiRepository, dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}