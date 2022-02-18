package com.appcrafters.covidtracker.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.appcrafters.covidtracker.R
import com.appcrafters.covidtracker.countrieslist.view.CountriesListFragment
import com.appcrafters.covidtracker.countrydetails.view.CountryDetailsFragment
import kotlinx.android.synthetic.main.fragment_covid_data_list.*

class MainActivity : AppCompatActivity(), ICoordinator {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        showFragment(CountriesListFragment(), true)
    }

    private fun showFragment(fragment: Fragment, addAsRoot: Boolean = false) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        if (!addAsRoot) transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showDetailsFragment(code: String) {
        showFragment(CountryDetailsFragment.newInstance(code))
    }

}