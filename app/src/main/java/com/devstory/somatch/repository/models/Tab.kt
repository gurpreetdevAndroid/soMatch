package com.devstory.somatch.repository.models

import androidx.fragment.app.Fragment


data class Tab(val tabFragment: Fragment, val tabName: String = "", val tabIcon: Int,
               val isShowTabName: Boolean)
