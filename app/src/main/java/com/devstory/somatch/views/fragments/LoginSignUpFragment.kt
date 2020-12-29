package com.devstory.somatch.views.fragments

import com.devstory.somatch.R
import com.devstory.somatch.viewmodels.BaseViewModel

class LoginSignUpFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_login_signup

    override fun init() {

    }

    override val viewModel: BaseViewModel?
        get() = null

    override fun observeProperties() {
    }

}