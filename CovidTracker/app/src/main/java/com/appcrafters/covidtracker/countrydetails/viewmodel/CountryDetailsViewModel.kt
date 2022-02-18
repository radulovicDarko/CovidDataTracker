package com.appcrafters.covidtracker.countrydetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcrafters.covidtracker.base.data.CovidTrackerDataSource
import com.appcrafters.covidtracker.base.functional.Either
import com.appcrafters.covidtracker.countrydetails.view.CountryDetailsViewState
import kotlinx.coroutines.launch

class CountryDetailsViewModel(private val dataSource: CovidTrackerDataSource) : ViewModel() {
    private val _state = MutableLiveData<CountryDetailsViewState>()
    val state: LiveData<CountryDetailsViewState>
        get() = _state

    fun getDataByCountryCode(code: String) {
        viewModelScope.launch {
            _state.postValue(CountryDetailsViewState.Proccessing)

            _state.postValue(
                when (val result = dataSource.getDataByCountryCode(code)) {
                    is Either.Success -> CountryDetailsViewState.DataReceived(result.data)
                    is Either.Error -> CountryDetailsViewState.ErrorReceived(result.exception.toString())
                }
            )
        }
    }
}