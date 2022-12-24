package com.arysugiarto.attendence.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.arysugiarto.attendence.R
import kotlinx.coroutines.*
import java.security.MessageDigest
import kotlin.math.round

/**
 * Showing [Snackbar] with certain Attribute Options
 * @param message used for display message to user
 * @param actionText is display action for user and callback for action with certain Callback
 * @param displayLength is Time for a [Snackbar] displayed to user
 * @param isAnchored used for anchoring [Snackbar] to Extended [View]
 * @param action Execute all process inside lambda function for [actionText] Callback
 */
fun View?.snack(
    message: CharSequence,
    actionText: CharSequence? = null,
    displayLength: Int = Snackbar.LENGTH_SHORT,
    @ColorInt backgroundColor: Int = Color.BLACK,
    @StyleRes resTextAppearance: Int = emptyInt,
    @ColorInt textColor: Int = Color.WHITE,
    isAnchored: Boolean = false,
    anchoredView: View? = this,
    action: ((View) -> Unit)? = null
) {
    this?.let {
        Snackbar.make(it, message, displayLength).apply {
            this.setBackgroundTint(
                if (backgroundColor != Color.BLACK) backgroundColor
                else context.color(R.color.silver)
            )

            with(view) {
                if (isAnchored) {
                    anchorView = anchoredView ?: this
                }

                findViewById<TextView>(R.id.snackbar_text).apply {
                    gravity = Gravity.CENTER
                    maxLines = 2
                    ellipsize = TextUtils.TruncateAt.END

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setTextAppearance(
                            if (resTextAppearance != emptyInt) resTextAppearance
                            else R.style.TextAppearance_OpenSans_Regular_Body2
                        )
                    }

                    this.setTextColor(
                        if (textColor != Color.WHITE) textColor
                        else Color.WHITE
                    )
                }

                if (!actionText.isNullOrEmpty()) {
                    findViewById<TextView>(R.id.snackbar_action)

                    setAction(actionText) {
                        action?.invoke(this)
                    }
                }
            }
        }.show()
    }
}

/**
 * This Function will Load any source to an ImageView with Desired format And Request
 * @param source is the imageSource with Type of Bitmap, Drawable, String, Uri , resDrawable, File, ByteArray And CustomModel
 * @param corner give an option of image transform type (Rounded, Circle, Rectangle)
 * @param overrideSize will resize image with desired pixel size
 * @param radius will round corner to desired size of [Int] pixels
 * @param shimmerLoad Condition to give shimmer placeholder load effect if condition set to true
 * @param background give background outside image source
 * @param scaleType is Scaling Type that will be displayed to user
 * @param placeHolder give a default image to ImageView when target image is loading or error
 */
fun ImageView.loadImage(
    source: Any?,
    corner: ImageCornerOptions? = ImageCornerOptions.NORMAL,
    radius: Int? = null,
    shimmerLoad: Boolean = false,
    overrideSize: Int? = null,
    placeHolder: Drawable? = ColorDrawable(Color.LTGRAY),
    @ColorRes background: Int? = null,
    scaleType: ImageView.ScaleType? = null
) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    when (corner) {
        ImageCornerOptions.NORMAL -> {
            requestOptions.transform(
                CenterCrop()
            )
        }

        ImageCornerOptions.CIRCLE -> {
            requestOptions.transform(
                CenterCrop(),
                RoundedCorners(
                    resources.getDimensionPixelSize(
                        R.dimen.image_options_circle_radius
                    )
                )
            )
        }

        ImageCornerOptions.ROUNDED -> {
            val cornerRadius = if (radius != null) {
                round(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        radius.toFloat(),
                        resources.displayMetrics
                    )
                ).toInt()
            } else {
                resources.getDimensionPixelSize(R.dimen.image_options_round_radius)
            }

            requestOptions.transform(
                CenterCrop(),
                RoundedCorners(cornerRadius)
            )
        }
    }

    source?.let {
        if (scaleType == ImageView.ScaleType.FIT_CENTER) requestOptions.fitCenter()
        else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) requestOptions.centerInside()

        if (overrideSize != null) {
            requestOptions.override(overrideSize)
        }

        Glide.with(context)
            .load(it)
            .apply(requestOptions)
            .into(this)

        if (background != null) {
            doOnPreDraw {
                setBackground(
                    context.createCircleDrawable(
                        Pair(measuredWidth, measuredHeight),
                        background
                    )
                )
            }
        }
    }
}

// Image Shape Options
enum class ImageCornerOptions {
    NORMAL, CIRCLE, ROUNDED
}

/**
 * Will Set Text to a TextView same as [TextView.setText] with nullable access reference
 * @param text used for display text to user
 * @param default default value when text inside [text] is Empty
 */
fun TextView.textOrNull(
    text: CharSequence?,
    default: String = ""
) {
    this.text = text.orEmpty(default)
}

/**
 * Setter & Getter for [TextView.textOrNull]
 * it's has Same Functionality as [TextView.setText] or [TextView.getText] with nullable access reference
 */
var TextView.textOrNull: CharSequence?
    get() = text.orEmpty
    set(value) = textOrNull(value)

/**
 * Set Desired Image into TextSpan Position of [Drawable] type
 */
fun TextView.setDrawable(
    drawableTop: Drawable? = null,
    drawableLeft: Drawable? = null,
    drawableBottom: Drawable? = null,
    drawableRight: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(
        drawableLeft, drawableTop, drawableRight, drawableBottom
    )
}

// Input Processing
@JvmName("getTextFunction")
fun TextInputLayout.getText(): String =
    editText?.text.toString()

fun TextInputLayout.textIsEmpty(): Boolean = editText?.text.toString().isEmpty()

fun TextInputLayout.warn(boxName: CharSequence? = "") {
    error = "Mohon isi kolom $boxName"
}


fun Activity?.hideKeyboard(view: View) {
    val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Set Desired Image into TextSpan Position of [Int] Resource type
 */
fun TextView.setDrawable(
    @DrawableRes drawableTop: Int? = null,
    @DrawableRes drawableLeft: Int? = null,
    @DrawableRes drawableBottom: Int? = null,
    @DrawableRes drawableRight: Int? = null
) {
    val top = drawableTop ?: return
    val left = drawableLeft ?: return
    val bottom = drawableBottom ?: return
    val right = drawableRight ?: return

    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

/**
 * Convert HTML into Formatted HTML Inside [TextView] with Image Compatibility
 * @param html source of Raw HTML
 */
fun TextView.htmlTextOrNull(html: String) {
    text = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(
                html, Html.FROM_HTML_MODE_LEGACY,
                htmlImageGetter, null
            ) as Spannable
        }
        else -> {
            @Suppress("DEPRECATION")
            Html.fromHtml(
                html,
                htmlImageGetter, null
            ) as Spannable
        }
    }
}

// Custom Html Image Getter with fit Transformation to DeviceWidth
private val TextView.htmlImageGetter
    get() = Html.ImageGetter { source ->
        val drawable = LevelListDrawable()
        val overrideWidth = context.deviceWidth - 120
        val overrideHeight = Target.SIZE_ORIGINAL

        Glide.with(context)
            .asBitmap()
            .load(source)
            .override(overrideWidth, overrideHeight)
            .transform(context.fitTransformation())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    drawable.apply {
                        addLevel(1, 1, BitmapDrawable(context.resources, resource))
                        setBounds(0, 0, resource.width, resource.height)
                        level = 1
                    }
                    invalidate()
                    text = text
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        drawable
    }

// Custom BitmapTransformation to make Image Fit to the screen
private fun Context.fitTransformation() = object : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("fit transformation".toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ) = toTransform.getScaledBitmapAtLongestSide(this@fitTransformation.deviceWidth - 120)
}

fun TextView.textWithClickSpan(
    charSequence: CharSequence? = null,
    spanOffset: Pair<Int, Int> = Pair(0, 0),
    @ColorRes spanColor: Int = R.color.black,
    clickAction: (View) -> Unit = {}
) {
    clickAction.let { action ->
        movementMethod = LinkMovementMethod.getInstance()

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                action(widget)
            }
        }

        text = buildSpannedString {
            append(charSequence?.subSequence(0..spanOffset.first.minus(1)))
            color(context.color(spanColor)) {
                bold {
                    append(
                        charSequence?.subSequence(spanOffset.first, spanOffset.second),
                        clickableSpan,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }
}

fun TextView.strike() {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * Set Text to a TextView with Click compatibility on Offset given
 * @param charSequence Text that will be displayed to user
 */
fun TextInputLayout.setText(charSequence: CharSequence? = "") {
    editText?.setText(charSequence)
}

var TextInputLayout.textOrEmpty: CharSequence
    get() = editText?.text.orEmpty
    set(value) = editText?.setText(value) ?: Unit

/** dialog style */
enum class DialogStyle {
    DEFAULT, SIMPLE, SINGLE, MULTI
}

fun Context.dialog(
    title: String? = null,
    message: String? = null,
    icon: Drawable? = null,
    centered: Boolean = false,
    isCancelable: Boolean = true,
    style: DialogStyle = DialogStyle.DEFAULT,
    items: Array<String> = emptyArray(),
    view: ((MaterialAlertDialogBuilder) -> View)? = null,
    positiveMessage: String? = null,
    onClickedAction: ((dialog: DialogInterface, position: Int) -> Unit)? = null,
    onMultiChoiceAction: ((dialog: DialogInterface, position: Int, checked: Boolean) -> Unit)? = null
): MaterialAlertDialogBuilder {
    val styleRes =
        if (centered) R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
        else R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog

    return MaterialAlertDialogBuilder(this, styleRes).apply {
        if (title != null) {
            setTitle(title)
        }
        if (message != null) {
            setMessage(message)
        }
        if (icon != null) {
            setIcon(icon)
        }
        if (view != null) {
            setView(view(this))
        }

        setCancelable(isCancelable)

        if (onClickedAction != null) {
            setPositiveButton(positiveMessage) { dialogInterface, position ->
                onClickedAction(dialogInterface, position)
            }
        }

        when {
            style == DialogStyle.DEFAULT && items.isEmpty() -> return@apply
            style == DialogStyle.SIMPLE && items.isNotEmpty() -> setItems(items) { dialog, which ->
                if (onClickedAction != null) onClickedAction(dialog, which)
            }

            style == DialogStyle.SINGLE && items.isNotEmpty() -> setSingleChoiceItems(
                items,
                0
            ) { dialog, which ->
                if (onClickedAction != null) onClickedAction(dialog, which)
            }

            style == DialogStyle.MULTI && items.isNotEmpty() -> setMultiChoiceItems(
                items,
                booleanArrayOf(false)
            ) { dialog, which, isChecked ->
                if (onMultiChoiceAction != null) onMultiChoiceAction(dialog, which, isChecked)
            }
        }
    }
}

fun Context.alertDialog(
    view: View,
    isCancelable: Boolean = true,
    fullScreen: Boolean = true
): AlertDialog {
    return dialog(view = { view }, isCancelable = isCancelable).create().apply {
        if (fullScreen) {
            val params = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }

            window?.attributes = params
        }
    }
}

/** Adding autoscrol for viewPager2
 * @param interval is used for the interval time of autoscroll
 * @param lifecycleScope is used to defined scope of viewpager2
 */
fun ViewPager2.autoScroll(lifecycleScope: LifecycleCoroutineScope, interval: Long) {
    lifecycleScope.launchWhenResumed {
        scrollIndefinitely(interval)
    }
}

private suspend fun ViewPager2.scrollIndefinitely(interval: Long) {
    delay(interval)
    val numberOfItems = adapter?.itemCount ?: 0
    val lastIndex = if (numberOfItems > 0) numberOfItems - 1 else 0
    val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1

    setCurrentItem(nextItem, true)

    scrollIndefinitely(interval)
}

fun EditText.addDelayOnTypeWithScope(
    delayMillis: Long = 0,
    scope: CoroutineScope,
    action: (String) -> Unit
) {
    var job: Job? = null

    addTextChangedListener {
        job?.cancel()

        job = scope.launch {
            delay(delayMillis)
            action(it.toString())
        }
    }
}
