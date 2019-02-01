package com.udacoding.drivergitfire.network

import com.udacoding.drivergitfire.utama.home.model.directions.ResultDirections
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("json")
    fun getRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ):Call<ResultDirections>
}