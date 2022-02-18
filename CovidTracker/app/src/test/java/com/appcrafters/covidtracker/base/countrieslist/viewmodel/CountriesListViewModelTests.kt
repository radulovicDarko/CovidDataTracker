package com.appcrafters.covidtracker.base.countrieslist.viewmodel

import androidx.lifecycle.Observer
import com.appcrafters.covidtracker.base.InstantExecutorTest
import com.appcrafters.covidtracker.base.TestCoroutineContextProvider
import com.appcrafters.covidtracker.base.data.ICovidTrackerDataSource
import com.appcrafters.covidtracker.base.functional.Either
import com.appcrafters.covidtracker.countrieslist.view.CountriesListViewState
import com.appcrafters.covidtracker.countrieslist.view.CountriesListViewState.*
import com.appcrafters.covidtracker.base.model.CovidData
import com.appcrafters.covidtracker.countrieslist.viewmodel.CountriesListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

class PokemonListViewModelTests : InstantExecutorTest() {
    @Mock
    lateinit var dataSource: ICovidTrackerDataSource

    @Mock
    lateinit var stateObserver: Observer<CountriesListViewState>

    lateinit var viewModel: CountriesListViewModel

    @Before
    fun setUp() {
        openMocks(this)
        viewModel = CountriesListViewModel(dataSource)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `testGetCountries, has result, state changed to Proccessing - DataReceived`() = runBlocking {
        val expectedResult: List<CovidData> = listOf()

        `when`(dataSource.getAllData()).thenReturn(Either.Success(expectedResult))

        viewModel.getCountries()

        verify(stateObserver).onChanged(Proccessing)
        verify(stateObserver).onChanged(DataReceived(expectedResult))
    }

    @Test
    fun `test GetCountries, has error, state changed to Proccessing - ErrorReceived`() = runBlocking {
        val expectedError = Exception("test")

        `when`(dataSource.getAllData()).thenReturn(Either.Error(expectedError))

        viewModel.getCountries()

        verify(stateObserver).onChanged(ErrorReceived(expectedError.toString()))
    }
}