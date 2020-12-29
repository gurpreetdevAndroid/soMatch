package com.devstory.somatch.views.utils

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by Mukesh on 05-10-2016.
 */
class SlidingConstraintLayout : ConstraintLayout {

    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle)

    var xFraction = 0f
        set(fraction) {
            if (0 == height) {
                if (null == preDrawListener) {
                    preDrawListener = ViewTreeObserver.OnPreDrawListener {
                        viewTreeObserver.removeOnPreDrawListener(
                                preDrawListener)
                        xFraction = fraction
                        true
                    }
                    viewTreeObserver.addOnPreDrawListener(preDrawListener)
                }
                return
            }

            val translationX = width * fraction
            setTranslationX(translationX)
            field = fraction
        }

    var yFraction = 0f
        set(fraction) {
            if (0 == height) {
                if (null == preDrawListener) {
                    preDrawListener = ViewTreeObserver.OnPreDrawListener {
                        viewTreeObserver.removeOnPreDrawListener(
                                preDrawListener)
                        yFraction = fraction
                        true
                    }
                    viewTreeObserver.addOnPreDrawListener(preDrawListener)
                }
                return
            }

            val translationY = height * fraction
            setTranslationY(translationY)
            field = fraction
        }
}