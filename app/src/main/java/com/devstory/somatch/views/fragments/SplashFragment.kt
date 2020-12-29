package com.devstory.somatch.views.fragments

import android.os.Handler
import com.devstory.somatch.R
import com.devstory.somatch.viewmodels.BaseViewModel
import com.devstory.somatch.views.activities.BaseAppCompactActivity
import com.devstory.somatch.views.activities.doFragmentTransaction

class SplashFragment :BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun init() {
        Handler().postDelayed({
            if (isAdded) {
                (activityContext as BaseAppCompactActivity).doFragmentTransaction(
                        fragment = LoginSignUpFragment(),
                        containerViewId = R.id.flFragContainerMain,
                        enterAnimation = R.animator.slide_right_in,
                        popExitAnimation = R.animator.slide_right_out,
                        isAddToBackStack = false
                )
            }
        }, 3000)
    }



    override val viewModel: BaseViewModel?
        get() = null

    override fun observeProperties() {

    }
}