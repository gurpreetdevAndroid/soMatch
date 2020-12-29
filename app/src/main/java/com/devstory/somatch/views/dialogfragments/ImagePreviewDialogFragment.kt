package com.devstory.somatch.views.dialogfragments

import android.os.Bundle
import android.view.View
import com.devstory.somatch.R
import com.devstory.somatch.views.adapters.ImagePreviewAdatper
import kotlinx.android.synthetic.main.dialogfragment_image_preview.*


/**
 * Created by Mukesh on 03-06-2016.
 */
class ImagePreviewDialogFragment : BaseDialogFragment() {

    companion object {

        const val BUNDLE_EXTRAS_IMAGES_LIST = "imagesList"
        const val BUNDLE_EXTRAS_IMAGE_POSITION = "imagePosition"

        fun newInstance(imagesList: ArrayList<String>, position: Int): ImagePreviewDialogFragment {
            val imagePreviewDialogFragment = ImagePreviewDialogFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(BUNDLE_EXTRAS_IMAGES_LIST, imagesList)
            bundle.putInt(BUNDLE_EXTRAS_IMAGE_POSITION, position)
            imagePreviewDialogFragment.arguments = bundle
            return imagePreviewDialogFragment
        }
    }

    override val layoutId: Int
        get() = R.layout.dialogfragment_image_preview

    override fun init() {
        // Get bundle data from arguments
        if (null != arguments) {
            val imagesList = arguments!!.getStringArrayList(BUNDLE_EXTRAS_IMAGES_LIST)

            if (null != imagesList) {
                viewPager.adapter = ImagePreviewAdatper(childFragmentManager, imagesList)

                if (1 == imagesList.size) {
                    circlePagerIndicator!!.visibility = View.GONE
                } else {
                    circlePagerIndicator!!.visibility = View.VISIBLE
                    circlePagerIndicator!!.setViewPager(viewPager!!)
                    viewPager!!.currentItem = arguments!!.getInt(BUNDLE_EXTRAS_IMAGE_POSITION, 0)
                }
            }
        }
    }

    override val isFullScreenDialog: Boolean
        get() = true

}
