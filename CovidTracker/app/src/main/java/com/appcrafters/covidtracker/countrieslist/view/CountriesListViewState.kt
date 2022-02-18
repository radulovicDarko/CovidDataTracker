package com.appcrafters.covidtracker.countrieslist.view

import com.appcrafters.covidtracker.base.model.CovidData

sealed class CountriesListViewState {

    object Proccessing : CountriesListViewState()
    data class DataReceived(val countries: List<CovidData>) :CountriesListViewState()
    data class ErrorReceived(val message: String) : CountriesListViewState()
}