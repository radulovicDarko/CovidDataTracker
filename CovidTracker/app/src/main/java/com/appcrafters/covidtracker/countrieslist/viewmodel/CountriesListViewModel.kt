package com.appcrafters.covidtracker.countrieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcrafters.covidtracker.base.data.ICovidTrackerDataSource
import com.appcrafters.covidtracker.base.functional.Either
import com.appcrafters.covidtracker.countrieslist.view.CountriesListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesListViewModel(private val dataSource: ICovidTrackerDataSource) : ViewModel() {

    private val _state = MutableLiveData<CountriesListViewState>()
    val state: LiveData<CountriesListViewState>
        get() = _state

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {

            _state.postValue(CountriesListViewState.Proccessing)

            _state.postValue(
                when (val result = dataSource.getAllData()) {
                    is Either.Success -> CountriesListViewState.DataReceived(result.data)
                    is Either.Error -> CountriesListViewState.ErrorReceived(result.exception.toString())
                }
            )
        }
    }
}