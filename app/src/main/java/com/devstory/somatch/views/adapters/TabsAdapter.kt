package com.devstory.somatch.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.devstory.somatch.repository.models.Tab


class TabsAdapter(private val fm: FragmentManager, private val tabsList: List<Tab>) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = tabsList[position].tabFragment

    override fun getCount(): Int = tabsList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return if (tabsList[position].isShowTabName) tabsList[position].tabName else ""
    }
}
