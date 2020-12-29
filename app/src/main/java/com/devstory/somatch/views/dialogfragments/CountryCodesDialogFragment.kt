package com.kaimzapp.views.dialgofragments

import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devstory.somatch.R
import com.devstory.somatch.repository.models.Country
import com.devstory.somatch.views.adapters.CountriesListAdapter
import com.devstory.somatch.views.dialogfragments.BaseDialogFragment
import com.google.gson.Gson

import kotlinx.android.synthetic.main.dialog_fragment_country_code.*
import kotlinx.android.synthetic.main.toolbar_dialog_fragments.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.*

/**
 * Created by Mukesh on 29/6/18.
 */
class CountryCodesDialogFragment : BaseDialogFragment() {

    companion object {
        const val INTENT_EXTRAS_COUNTRY = "country"
        const val REQUEST_CODE_COUNTRY_CODES_DIALOG_FRAGMENT = 432
    }

    override val isFullScreenDialog: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.dialog_fragment_country_code

    override fun init() {
        // Set Toolbar
        toolbar.setBackgroundColor(
                ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorTransparent
                )
        )
        toolbar.navigationIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_back)
        tvToolbarTitle.setTextColor(
                ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPrimaryText
                )
        )

        // Set Toolbar title
        tvToolbarTitle.text = getString(R.string.select_country_code)

        // Set RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        activity,
                        LinearLayoutManager.VERTICAL
                )
        )
        val mCountriesListAdapter = CountriesListAdapter(this, countriesList)
        recyclerView.adapter = mCountriesListAdapter

        // handle search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                mCountriesListAdapter.searchCountry(newText.trim().toLowerCase())
                return false
            }
        })
    }

    private val countriesList: ArrayList<Country>
        get() {
            val countriesList = ArrayList<Country>()
            val inputStream = resources.openRawResource(R.raw.countries_data)
            try {
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                if (jsonString.isNotEmpty()) {
                    val jsonArray = JSONArray(jsonString)
                    val gson = Gson()
                    (0 until jsonArray.length()).mapTo(countriesList) {
                        gson.fromJson(
                                jsonArray.getString(it),
                                Country::class.java
                        )
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return countriesList
        }
}