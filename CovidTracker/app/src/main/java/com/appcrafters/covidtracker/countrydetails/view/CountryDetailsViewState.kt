package com.appcrafters.covidtracker.countrydetails.view

import com.appcrafters.covidtracker.base.model.CovidData

sealed class CountryDetailsViewState {
    object Proccessing: CountryDetailsViewState()
    data class DataReceived(val countryData: CovidData) : CountryDetailsViewState()
    data class ErrorReceived(val message: String) : CountryDetailsViewState()
}