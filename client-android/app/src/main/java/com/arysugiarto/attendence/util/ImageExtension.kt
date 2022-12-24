package com.arysugiarto.attendence.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.ColorRes
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.util.Const.File.Image.defaultFileName
import java.io.ByteArrayOutputStream

fun Context.createCircleDrawable(
    whSize: Pair<Int,Int>,
    @ColorRes backgroundColor: Int = R.color.black
): GradientDrawable {
    return GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        cornerRadii = floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f)
        color = colorStateList(backgroundColor)
        setSize(whSize.first, whSize.second)
    }
}

fun Bitmap?.getScaledBitmapAtLongestSide(
    targetSize: Int
): Bitmap? {
    if (this == null || width <= targetSize && height <= targetSize) return this

    val targetWidth: Int
    val targetHeight: Int

    when {
        height > width -> {
            targetHeight = targetSize
            val percentage = targetSize.toFloat() / height
            targetWidth = (width * percentage).toInt()
        }
        else -> {
            targetWidth = targetSize
            val percentage = targetSize.toFloat() / width
            targetHeight = (height * percentage).toInt()
        }
    }

    return Bitmap.createScaledBitmap(this, targetWidth, targetHeight, true)
}

@Suppress("Deprecation")
fun Bitmap?.save(
    context: Context,
    fileName: String? = defaultFileName,
    quality: Int = 100,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
): Uri? {
    return this?.let {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            compress(format, quality, ByteArrayOutputStream())
            Uri.parse(
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    it,
                    fileName + System.currentTimeMillis(),
                    null
                )
            )
        } else {
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    fileName + System.currentTimeMillis()
                )
                put(
                    MediaStore.MediaColumns.MIME_TYPE,
                    Const.File.MimeType.image
                )
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/${Const.File.Location.basePath}${Const.File.Location.storePath}"
                )
                put(
                    MediaStore.MediaColumns.IS_PENDING,
                    Const.File.Pending.isPending
                )
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                contentValues
            )

            uri?.let { currentUri ->
                resolver.openOutputStream(currentUri).use { output ->
                    compress(format, quality, output)
                }
            }

            contentValues.apply {
                clear()
                put(
                    MediaStore.MediaColumns.IS_PENDING,
                    Const.File.Pending.notPending
                )
            }

            uri?.apply {
                resolver.update(
                    this,
                    contentValues,
                    null,
                    null
                )
            }
        }
    }
}