package com.appcrafters.covidtracker.countrieslist.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.appcrafters.covidtracker.base.model.CovidData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_covid_data_details.*
import kotlinx.android.synthetic.main.item_country_data.view.*
import java.text.SimpleDateFormat
import java.util.*

class CountryRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(covidData: CovidData, onItemClicked: (String) -> Unit) {

        itemView.countryNameTxt.text = covidData.country

        val dateFormatMask = SimpleDateFormat("MM.DD.yyyy.")
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(covidData.updated)

        itemView.lastUpdatedTxt.text = "Updated at " + dateFormatMask.format(calendar.getTime())

        Glide.with(itemView).load(covidData.countryInfo.flag).into(itemView.countryFlagImg)

        itemView.setOnClickListener { onItemClicked.invoke(covidData.countryInfo.iso2) }
    }
}