package com.kv.rachtr.domain.repository

import com.kv.rachtr.data.remote.HomeService
import com.kv.rachtr.domain.model.TodoModel
import retrofit2.Response
import java.net.UnknownHostException


class HomeRepository(val api: HomeService) {

    suspend fun getTodo(): Response<ArrayList<TodoModel>>? {
        try {
            val response = api.getTodo()
            response.let {
                return it
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null;
    }
}