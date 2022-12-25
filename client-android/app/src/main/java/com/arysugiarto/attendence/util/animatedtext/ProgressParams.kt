package com.arysugiarto.attendence.util.animatedtext

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.arysugiarto.attendence.util.animatedtext.AnimatedText
import com.arysugiarto.attendence.util.animatedtext.DrawableParams


/**
 * Use to customize your progress drawable
 * The final progress drawable size will be = (radius + stroke) * 2
 */
open class ProgressParams : DrawableParams() {

    /**
     * Dimension resource used for the progress radius
     * The default value is 7.5dp
     */
    @DimenRes
    var progressRadiusRes: Int? = null

    /**
     * Progress radius size in pixels
     * The default value is 7.5dp
     */
    var progressRadiusPx: Int = AnimatedText.DEFAULT

    /**
     * Dimension resource used for the progress stroke
     * The default value is 2.5dp
     */
    @DimenRes
    var progressStrokeRes: Int? = null

    /**
     * Progress stroke size in pixels
     * The default value is 2.5dp
     */
    var progressStrokePx: Int = AnimatedText.DEFAULT

    /**
     * Single color int value used for the progress
     */
    @ColorInt
    var progressColor: Int? = null

    /**
     * Single color resource value used for the progress
     */
    @ColorRes
    var progressColorRes: Int? = null

    /**
     * List of color int values used for the progress
     */
    var progressColors: IntArray? = null
}