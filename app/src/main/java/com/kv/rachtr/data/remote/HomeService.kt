package com.kv.rachtr.data.remote

import com.kv.rachtr.domain.model.TodoModel
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("/todos")
    suspend fun getTodo(): Response<ArrayList<TodoModel>>
}