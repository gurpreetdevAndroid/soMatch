package com.devstory.somatch.views.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.devstory.somatch.R
import com.devstory.somatch.repository.models.Country
import com.devstory.somatch.viewmodels.BaseViewModel
import com.facebook.common.util.UriUtil
import com.kaimzapp.views.dialgofragments.CountryCodesDialogFragment
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class LoginFragment  :BaseFragment(),View.OnClickListener
{
    private var countryISO = ""
    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun init() {

        // Set click listener
        liCountry.setOnClickListener(this)

    }

    override val viewModel: BaseViewModel?
        get() = null

    override fun observeProperties() {
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.liCountry->
            {
                val countryCodesDialogFragment = CountryCodesDialogFragment()
                countryCodesDialogFragment.setTargetFragment(
                        this, CountryCodesDialogFragment
                        .REQUEST_CODE_COUNTRY_CODES_DIALOG_FRAGMENT
                )
                countryCodesDialogFragment.show(requireFragmentManager(), getString(R.string.dialog))
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (Activity.RESULT_OK == resultCode && CountryCodesDialogFragment
                        .REQUEST_CODE_COUNTRY_CODES_DIALOG_FRAGMENT == requestCode
        ) {




            intent?.getParcelableExtra<Country>(
                    CountryCodesDialogFragment
                            .INTENT_EXTRAS_COUNTRY
            )?.let { country ->
                with(country) {



                    countryISO = if (code.equals("do", true)) {
                        "do_flag"
                    } else {
                        code.toLowerCase(Locale.US)
                    }

                    sdvFlagImage.setImageURI(
                            Uri.Builder()
                                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                                    .path(
                                            resources.getIdentifier(
                                                    countryISO, "drawable",
                                                    activityContext.packageName
                                            ).toString()
                                    )
                                    .build(),
                            activityContext
                    )

                    tvCountryCode.text = dial_code
                    tvCountryName.text = name
                }
            }
        }
    }

}