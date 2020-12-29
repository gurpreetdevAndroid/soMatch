package com.devstory.somatch.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.devstory.somatch.views.fragments.ImageFragment

/**
 * Created by Mukesh on 24/05/2016.
 */
class ImagePreviewAdatper(fragmentManager: FragmentManager,
                          private val imagesList: ArrayList<String>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment =
            ImageFragment.newInstance(imagesList[position])

    override fun getCount(): Int = imagesList.size
}
