package com.arysugiarto.attendence.util.animatedtext

import android.graphics.Rect
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.TransformationMethod
import android.view.View
import java.util.*

class AllCapsSpannedTransformationMethod : TransformationMethod {

    override fun getTransformation(source: CharSequence?, view: View): CharSequence? {
        if (source == null) {
            return null
        }
        val upperCaseText = source.toString().uppercase(Locale.getDefault())
        return if (source is Spanned) {
            val spannable = SpannableString(upperCaseText)
            TextUtils.copySpansFrom(source, 0, source.length, null, spannable, 0)
            spannable
        } else {
            upperCaseText
        }
    }

    override fun onFocusChanged(
        view: View, sourceText: CharSequence, focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
    }

}
