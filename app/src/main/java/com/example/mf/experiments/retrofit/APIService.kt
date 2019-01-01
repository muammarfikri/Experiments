package com.example.mf.experiments.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface APIService{
    @GET("/Items")
    fun getUsers() : Deferred<List<UserResponse>>
}

