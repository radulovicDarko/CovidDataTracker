package com.appcrafters.covidtracker.base.data

import com.appcrafters.covidtracker.base.functional.Either
import com.appcrafters.covidtracker.base.model.CovidData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

interface ICovidTrackerDataSource {
    suspend fun getAllData(): Either<List<CovidData>>
    suspend fun getDataByCountryCode(code: String): Either<CovidData>
}

class CovidTrackerDataSource(private val apiService: CovidTrackerApiService) : ICovidTrackerDataSource {
    companion object {
        private const val host = "covid-19-tracking.p.rapidapi.com"
        private const val key = "ea9d745207msh8cd03420e084d49p1ac50ajsn5f5967a56706"
    }

    override suspend fun getAllData(): Either<List<CovidData>> = handleCall(apiService.getAllData())

    override suspend fun getDataByCountryCode(code: String): Either<CovidData> = handleCall(apiService.getDataByCountryCode(code))

    private suspend fun <T> handleCall(call: Call<T>): Either<T> {
        return withContext(Dispatchers.IO) {
            val response = call.execute()

            if (response.isSuccessful) {
                Either.Success(response.body()!!)
            } else {
                Either.Error(Exception(response.message()))
            }
        }
    }
}