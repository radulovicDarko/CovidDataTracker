package com.appcrafters.covidtracker.countrydetails.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appcrafters.covidtracker.R
import com.appcrafters.covidtracker.base.data.ApiServiceProvider
import com.appcrafters.covidtracker.base.data.CovidTrackerDataSource
import com.appcrafters.covidtracker.base.functional.ViewModelFactoryUtil
import com.appcrafters.covidtracker.base.model.CovidData
import com.appcrafters.covidtracker.countrydetails.viewmodel.CountryDetailsViewModel
import kotlinx.android.synthetic.main.fragment_covid_data_details.*
import kotlinx.android.synthetic.main.item_country_data.*
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class CountryDetailsFragment : Fragment() {

    var country: String = "-1"
    private lateinit var _viewModel: CountryDetailsViewModel

    companion object {

        private const val COUNTRY_ID_KEY = "COUNTRY_ID_KEY"

        fun newInstance(code: String): CountryDetailsFragment {

            return CountryDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(COUNTRY_ID_KEY, code)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        country = arguments?.getString(COUNTRY_ID_KEY) ?: "-1"
        _viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            CountryDetailsViewModel(CovidTrackerDataSource(ApiServiceProvider.covidTrackerApiService))
        }).get(CountryDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_covid_data_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFromViewModel()
        print(country)
        _viewModel.getDataByCountryCode(country)
    }

    private fun bindFromViewModel() {
        _viewModel.state.observe(viewLifecycleOwner) { state ->

            covidDataDetailsProgressBar.isVisible = state is CountryDetailsViewState.Proccessing

            when (state) {
                is CountryDetailsViewState.DataReceived -> {
                    setUpView(state.countryData)
                }
                is CountryDetailsViewState.ErrorReceived -> {
                    showError(state.message)
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpView(data: CovidData) {
        countryTxt.text = data.country
        val dateFormatMask = SimpleDateFormat("MM.DD.yyyy.")
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(data.updated)

        updatedTxt.text = "Updated at " + dateFormatMask.format(calendar.getTime())

        totalConfirmTxt.text = NumberFormat.getInstance().format(data.cases)
        todayConfirmTxt.text = "( +" + (0..1000).random() + " )"
        totalActiveTxt.text = NumberFormat.getInstance().format(data.active)
        totalRecoveredTxt.text = NumberFormat.getInstance().format(data.recovered)
        todayRecoveredTxt.text = "( +" + (0..1000).random() + " )"
        totalDeathTxt.text = NumberFormat.getInstance().format(data.deaths)
        todayDeathTxt.text = "( +" + (0..100).random() + " )"
        totalTestsTxt.text = NumberFormat.getInstance().format(data.tests)

        piechart.addPieSlice(PieModel("Confirm", data.cases.toFloat(), Color.parseColor("#FBC233")))
        piechart.addPieSlice(PieModel("Active", data.active.toFloat(),Color.parseColor("#007AFE")))
        piechart.addPieSlice(PieModel("Recovered", data.recovered.toFloat(),Color.parseColor("#08A045")))
        piechart.addPieSlice(PieModel("Deaths", data.deaths.toFloat(),Color.parseColor("#F6404F")))

        piechart.startAnimation()
    }
}
