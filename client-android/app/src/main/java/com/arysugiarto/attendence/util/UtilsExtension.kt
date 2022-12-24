package com.arysugiarto.attendence.util

import android.content.*
import android.net.Uri
import android.util.DisplayMetrics
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.card.MaterialCardView
import com.arysugiarto.attendence.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

val Context?.appVersionNumber: String get() {
    return try {
        this?.applicationContext?.let {
            val packageInfo = it.packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName
        }.orEmpty
    } catch (e: Exception) {
        "Version Mismatch" // Also Can Assigned to Null
    }
}

fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (resources
        .displayMetrics
        .densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

const val ALLOWED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm"

val Int.getRandomCharacters: String get() = List(this) {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    allowedChars.random()
}.joinToString(emptyString)


fun Int.getRandomAlphabet(): String {
    val random = Random()
    val sb = StringBuilder(this)
    for (i in 0 until this)
        sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
    return sb.toString()
}

fun getSessionId() : String {
    val tsLong = System.currentTimeMillis() / 1000
    val ts = tsLong.toString()
    return ts + 10.getRandomAlphabet()
}

fun LifecycleOwner.coroutinesCountDown(
    delayMillis: Long = 0,
    repeatMillis: Long = 0,
    action: suspend () -> Unit
) = lifecycleScope.launch {
    delay(delayMillis)
    if (repeatMillis > 0) {
        while (true) {
            action.invoke()
            delay(repeatMillis)
        }
    } else action()
}

fun Context.clip(
    source: CharSequence,
    label: CharSequence = javaClass.simpleName
) {
    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    manager.setPrimaryClip(ClipData.newPlainText(label, source))
}

fun Context?.toast(
    message: CharSequence?,
    length: Int = Toast.LENGTH_SHORT
) {
    this?.let { context ->
        Toast.makeText(context, message, length)?.apply {
            view?.apply {
                findViewById<TextView>(android.R.id.message)?.apply {
                    typeface = font(R.font.open_sans_regular)
                }
            }
        }?.show()
    }
}

fun youtubeIntent(context : Context, description: String?, videoId: String?, videoUrl: String?) {
    try {
        val intent = if (description.equals("channel")) {
            Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://channel/$videoId"))
        } else {
            Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$videoId"))
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(videoUrl)
        )
        context.startActivity(i)
    }
}

fun MaterialCardView.statusOrderCount(totalItem: Int, tv: TextView){
    when {
        totalItem > 99 -> tv.text = "99+"
        totalItem <= 0 -> isVisible = false
        else -> tv.text = totalItem.toString()
    }
}

fun Context.shareTextToOtherPlatforms(title: String? = "Bagikan Product", shareText: String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}
