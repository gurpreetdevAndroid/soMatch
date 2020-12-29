package com.devstory.somatch.views.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devstory.somatch.R
import com.devstory.somatch.repository.models.Country
import com.kaimzapp.views.dialgofragments.CountryCodesDialogFragment
import kotlinx.android.synthetic.main.row_country.view.*

/**
 * Created by Mukesh on 29/6/18.
 */
class CountriesListAdapter(private val mCounCountryCodesDialogFragment: CountryCodesDialogFragment,
                           private val countriesList: ArrayList<Country>) :
        RecyclerView.Adapter<CountriesListAdapter.CountryCodeViewHolder>() {

    private val originalCountryCodesList = ArrayList<Country>()
    private val mLayoutInflater: LayoutInflater

    init {
        originalCountryCodesList.addAll(countriesList)
        mLayoutInflater = LayoutInflater.from(mCounCountryCodesDialogFragment.activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryCodeViewHolder =
            CountryCodeViewHolder(mLayoutInflater.inflate(R.layout.row_country,
                    parent, false))

    override fun onBindViewHolder(countryCodeViewHolder: CountryCodeViewHolder, position: Int) {
        countryCodeViewHolder.bindCountry(countriesList[position])
    }

    override fun getItemCount(): Int = countriesList.size

    fun searchCountry(searchedKeyword: String) {
        countriesList.clear()
        if (searchedKeyword.isEmpty()) {
            countriesList.addAll(originalCountryCodesList)
        } else {
            originalCountryCodesList.filterTo(countriesList) {
                it.name.toLowerCase()
                        .contains(searchedKeyword)
            }
        }
        notifyDataSetChanged()
    }

    inner class CountryCodeViewHolder(private val containerView: View) : RecyclerView
    .ViewHolder(containerView) {

        init {
            containerView.llParent.setOnClickListener {
                mCounCountryCodesDialogFragment.dismiss()
                if (null != mCounCountryCodesDialogFragment.targetFragment) {
                    mCounCountryCodesDialogFragment.targetFragment!!
                            .onActivityResult(mCounCountryCodesDialogFragment
                                    .targetRequestCode, Activity.RESULT_OK,
                                    Intent().putExtra(CountryCodesDialogFragment
                                            .INTENT_EXTRAS_COUNTRY,
                                            countriesList[adapterPosition]
                                                    .dial_code))
                }
            }
        }

        fun bindCountry(country: Country) {
            with(country) {
                containerView.tvCountryName.text = name
                containerView.tvCountryCode.text = dial_code
            }
        }
    }

}