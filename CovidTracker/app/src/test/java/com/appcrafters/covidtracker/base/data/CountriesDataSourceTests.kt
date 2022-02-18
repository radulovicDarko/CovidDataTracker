package com.appcrafters.covidtracker.base.data

import com.appcrafters.covidtracker.base.functional.Either
import com.appcrafters.covidtracker.base.model.CountryInfo
import com.appcrafters.covidtracker.base.model.CovidData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import retrofit2.Call
import retrofit2.Response

class CountriesDataSourceTests {

    @Mock
    lateinit var apiService: CovidTrackerApiService

    @Mock
    lateinit var getCountriesCall: Call<List<CovidData>>

    @Mock
    lateinit var getCountryDetailsCall: Call<CovidData>

    lateinit var dataSource: CovidTrackerDataSource

    @Before
    fun setUp() {
        openMocks(this)
        dataSource = CovidTrackerDataSource(apiService)
    }

    @Test
    fun `testGetCountries, has response, Success returned`() = runBlocking {
        val expectedCountries: List<CovidData> = listOf()
        val expectedResult = Either.Success(expectedCountries)

        `when`(apiService.getAllData()).thenReturn(getCountriesCall)
        `when`(getCountriesCall.execute()).thenReturn(Response.success(expectedCountries))

        val result = dataSource.getAllData()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `testGetCountries, has error, Error returned`() = runBlocking {
        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("application/json"), ""
        )

        `when`(apiService.getAllData()).thenReturn(getCountriesCall)
        `when`(getCountriesCall.execute()).thenReturn(Response.error(400, expectedResponseBody))


        val result = dataSource.getAllData()

        assertTrue(result is Either.Error)
    }

    @Test
    fun `testGetCountryDetails, has response, Success returned`() = runBlocking {
        val countryInfo: CountryInfo= CountryInfo(4,"AF","AFG",33.2,65.1,"https://disease.sh/assets/img/flags/af.png")
        val expectedCountryDetails: CovidData = CovidData(
            1642247344715,
            "Afghanistan",
            countryInfo,
            158639,
            0,
            7376,
            0,
            145906,
            0,
            5357,
            1124,
            3939,
            183,
            834349,
            20718,
            40272489,
            "Asia",
            254,
            5460,
            48,
            133.02,
            3622.97,
            27.91
        )

        val expectedResult = Either.Success(expectedCountryDetails)

        `when`(apiService.getDataByCountryCode(anyString())).thenReturn(
            getCountryDetailsCall
        )
        `when`(getCountryDetailsCall.execute()).thenReturn(Response.success(expectedCountryDetails))

        val result = dataSource.getDataByCountryCode("afg")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `testGetCountryDetails, has error, Error returned`() = runBlocking {
        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("application/json"), ""
        )

        `when`(apiService.getDataByCountryCode(anyString())).thenReturn(
            getCountryDetailsCall
        )
        `when`(getCountryDetailsCall.execute()).thenReturn(Response.error(400, expectedResponseBody))


        val result = dataSource.getDataByCountryCode("afg")

        assertTrue(result is Either.Error)
    }
}