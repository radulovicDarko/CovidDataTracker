package com.appcrafters.covidtracker.countrieslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appcrafters.covidtracker.R
import com.appcrafters.covidtracker.base.ICoordinator
import com.appcrafters.covidtracker.base.data.ApiServiceProvider
import com.appcrafters.covidtracker.base.data.CovidTrackerDataSource
import com.appcrafters.covidtracker.base.functional.ViewModelFactoryUtil
import com.appcrafters.covidtracker.base.model.CovidData
import com.appcrafters.covidtracker.countrieslist.recycler.CountryRVAdapter
import com.appcrafters.covidtracker.countrieslist.viewmodel.CountriesListViewModel
import kotlinx.android.synthetic.main.fragment_covid_data_list.*

class CountriesListFragment : Fragment() {
    lateinit var viewModel: CountriesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            CountriesListViewModel(CovidTrackerDataSource(ApiServiceProvider.covidTrackerApiService))
        }).get(CountriesListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_covid_data_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindFromViewModel()
        viewModel.getCountries()
    }

    private fun setUpRecyclerView(countries: List<CovidData>) {
        countriesListRV.adapter = CountryRVAdapter(countries) { code ->
            (activity as ICoordinator).showDetailsFragment(code)
        }
    }

    private fun bindFromViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            countryListProgressBar.isVisible = state is CountriesListViewState.Proccessing

            when (state) {
                is CountriesListViewState.DataReceived -> {
                    setUpRecyclerView(state.countries)
                }
                is CountriesListViewState.ErrorReceived -> showError(state.message)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}