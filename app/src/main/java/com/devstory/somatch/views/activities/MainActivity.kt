package com.devstory.somatch.views.activities

import com.devstory.somatch.R
import com.devstory.somatch.views.fragments.LoginFragment
import com.devstory.somatch.views.fragments.SplashFragment

class MainActivity : BaseAppCompactActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val isMakeStatusBarTransparent: Boolean
        get() = true

    override fun init() {
        // Set Splash Screen
        doFragmentTransaction(
                fragment = LoginFragment(),
                containerViewId = R.id.flFragContainerMain,
                enterAnimation = R.animator.slide_right_in,
                popExitAnimation = R.animator.slide_right_out, isAddToBackStack = false
        )
    }

}
