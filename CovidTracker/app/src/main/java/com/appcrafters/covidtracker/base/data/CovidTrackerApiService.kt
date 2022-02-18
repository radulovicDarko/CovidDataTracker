package com.appcrafters.covidtracker.base.data

import com.appcrafters.covidtracker.base.model.CovidData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidTrackerApiService {

    @GET("countries")
    fun getAllData(): Call<List<CovidData>>

    @GET("countries/{country}")
    fun getDataByCountryCode(@Path("country") country: String): Call<CovidData>
}