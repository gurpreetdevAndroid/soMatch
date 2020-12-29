package com.devstory.somatch.repository.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mukesh on 19/7/18.
 */
@Parcelize
data class Country(val name: String = "", val dial_code: String = "", val code: String = ""):Parcelable