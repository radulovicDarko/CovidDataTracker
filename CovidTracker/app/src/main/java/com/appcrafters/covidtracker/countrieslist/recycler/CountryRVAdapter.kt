package com.appcrafters.covidtracker.countrieslist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.appcrafters.covidtracker.R
import com.appcrafters.covidtracker.base.model.CovidData

class CountryRVAdapter(private val countries: List<CovidData>, private val onItemClicked: (String) -> Unit) :
    Adapter<CountryRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryRVViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country_data, parent, false)
    )

    override fun onBindViewHolder(holder: CountryRVViewHolder, position: Int) {
        holder.bind(countries[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}