package com.appcrafters.covidtracker.base.data

object ApiServiceProvider {
    val covidTrackerApiService = RetrofitBuilder.retrofit.create(CovidTrackerApiService::class.java)
}