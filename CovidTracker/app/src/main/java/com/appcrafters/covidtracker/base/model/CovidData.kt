package com.appcrafters.covidtracker.base.model

data class CovidData(
    val updated: Long,
    val country: String,
    val countryInfo: CountryInfo,
    val cases: Int,
    val todayCases: Int,
    val deaths: Int,
    val todayDeaths: Int,
    val recovered: Int,
    val todayRecovered: Int,
    val active: Int,
    val critical: Int,
    val casesPerOneMillion: Int,
    val deathsPerOneMillion: Int,
    val tests: Int,
    val testsPerOneMillion: Int,
    val population: Int,
    val continent: String,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val activePerOneMillion: Double,
    val recoveredPerOneMillion: Double,
    val criticalPerOneMillion: Double,
)

data class CountryInfo (
    val _id: Int,
    val iso2: String,
    val iso3: String,
    val lat: Double,
    val long: Double,
    val flag: String,
)