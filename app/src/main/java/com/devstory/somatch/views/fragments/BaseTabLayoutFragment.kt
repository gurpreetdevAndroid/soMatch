package com.devstory.somatch.views.fragments

import android.annotation.TargetApi
import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.devstory.somatch.R
import com.devstory.somatch.repository.models.Tab
import com.devstory.somatch.utils.GeneralFunctions
import com.devstory.somatch.views.adapters.TabsAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fargment_base_tab_layout.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Mukesh on 16/5/18.
 */
abstract class BaseTabLayoutFragment : BaseFragment() {

    override fun init() {
        if (null != toolbar) {
            // remove toolbar_home background and elevation
            if (GeneralFunctions.isAboveLollipopDevice) {
                toolbar.elevation = 0f
            }
//            toolbar_home.setBackgroundColor(Color.TRANSPARENT)
        }
        initTabs()
    }

    fun setViewPager(toolbarTitle: String, tabsList: List<Tab>) {
        if (null != tvToolbarTitle) {
            tvToolbarTitle.text = toolbarTitle
        }

        val tabsAdapter = TabsAdapter(childFragmentManager, tabsList)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = tabsAdapter

        if (null != tabLayout) {
            if (3 < tabsList.size) {
                tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            }
            tabLayout.setupWithViewPager(viewPager)
            changeTabsFont(tabLayout)
        }
    }

    // Change Tab title font
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeTabsFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface = ResourcesCompat.getFont(activityContext,
                            R.font.font_mont_semibold)
                }
            }
        }
    }

    abstract fun initTabs()

}