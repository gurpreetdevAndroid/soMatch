package com.devstory.somatch.views.fragments

import android.net.Uri
import android.os.Bundle
import com.devstory.somatch.R
import com.devstory.somatch.utils.GeneralFunctions
import com.devstory.somatch.viewmodels.BaseViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File


/**
 * Created by Mukesh on 03/06/2016.
 */
class ImageFragment : BaseFragment() {

    companion object {

        const val BUNDLE_EXTRAS_IMAGE = "image"

        fun newInstance(image: String): ImageFragment {
            val imageFragment = ImageFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_EXTRAS_IMAGE, image)
            imageFragment.arguments = bundle
            return imageFragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_image

    override fun init() {
        // Get image from arguments
        val image = arguments?.getString(BUNDLE_EXTRAS_IMAGE) ?: ""

        // check if image is a local file or url
        if (image.contains("http")) {
            setImage(GeneralFunctions.getResizedImage(image, 0, 0))
        } else {
            val imageFile = File(image)
            if (imageFile.exists()) {
                setImage(GeneralFunctions.getLocalImageFile(imageFile))
            }
        }
    }

    private fun setImage(image: String) {
        zdvImage.setAllowTouchInterceptionWhileZoomed(true)
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(image))
                .build()
        zdvImage.controller = controller
    }

    override val viewModel: BaseViewModel?
        get() = null

    override fun observeProperties() {
    }

}
