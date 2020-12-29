package com.devstory.somatch.views.activities

import com.devstory.somatch.R

class HomeActivity : BaseAppCompactActivity() {
    override val layoutId: Int
        get() = R.layout.activity_home
    override val isMakeStatusBarTransparent: Boolean
        get() = false

    override fun init() {
    }
}