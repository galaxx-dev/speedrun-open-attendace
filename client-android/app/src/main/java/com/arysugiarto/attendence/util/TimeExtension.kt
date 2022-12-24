package com.arysugiarto.attendence.util

import androidx.fragment.app.FragmentManager
import com.arysugiarto.attendence.R
import com.google.android.material.datepicker.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun FragmentManager.timePicker(
    selection: Long? = null,
    title: String? = null,
    fullScreen: Boolean = false,
    constraint: Pair<Long?, Long?>? = null,
    onSaveClickAction: ((Long) -> Unit)?
): MaterialDatePicker<Long> {

    val constraintBuilderRange = CalendarConstraints.Builder()

    constraintBuilderRange.setStart(constraint?.first.orEmpty)
    constraintBuilderRange.setEnd(constraint?.second.orEmpty)

    val minValidator = DateValidatorPointForward.from(constraint?.first.orEmpty)
    val maxValidator = DateValidatorPointBackward.before(constraint?.second.orEmpty)

    val validators = listOf(minValidator, maxValidator)
    val dateValidator = CompositeDateValidator.allOf(validators)

    constraintBuilderRange.setValidator(dateValidator)

    return MaterialDatePicker.Builder.datePicker().apply {
        if (selection != null) setSelection(selection)
        if (constraint != null) setCalendarConstraints(constraintBuilderRange.build())

        if (constraint != null) setCalendarConstraints(constraintBuilderRange.build())

        setTheme(
            if (!fullScreen) R.style.ThemeOverlay_MaterialComponents_MaterialCalendar
            else R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen
        )

        if (!title.isNullOrEmpty()) {
            setTitleText(title)
        }

    }.build().apply {
        addOnPositiveButtonClickListener {
            if (onSaveClickAction != null) {
                onSaveClickAction(it)
            } else dismiss()
        }
    }.also {
        it.show(this, javaClass.simpleName)
    }
}

fun FragmentManager.rangeTimePicker(
    selectedRange: Pair<Long, Long>? = null,
    title: String? = null,
    constraint: Pair<Date?, Date?>? = null,
    fullScreen: Boolean = false,
    onSaveClickAction: ((Pair<Long, Long>) -> Unit)?
): MaterialDatePicker<androidx.core.util.Pair<Long, Long>> {
    val constraintBuilder = CalendarConstraints.Builder()
    val minValidator = DateValidatorPointForward.from(constraint?.first?.time.orEmpty)
    val maxValidator = DateValidatorPointBackward.before(constraint?.second?.time.orEmpty)

    val validators = listOf(minValidator, maxValidator)
    val dateValidator = CompositeDateValidator.allOf(validators)
    constraintBuilder.setValidator(dateValidator)

    return MaterialDatePicker.Builder.dateRangePicker().apply {
        if (!title.isNullOrEmpty()) {
            setTitleText(title)
        }

        if (constraint != null) setCalendarConstraints(constraintBuilder.build())

        setTheme(
            if (!fullScreen) R.style.ThemeOverlay_MaterialComponents_MaterialCalendar
            else R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen
        )

        if (selectedRange != null) {
            setSelection(androidx.core.util.Pair(selectedRange.first, selectedRange.second))
        }

    }.build().apply {
        addOnPositiveButtonClickListener {
            if (onSaveClickAction != null) {
                onSaveClickAction(Pair(it.first.orEmpty(), it.second.orEmpty()))
            } else dismiss()
        }
    }.also {
        it.show(this, javaClass.simpleName)
    }
}


fun getTimeZoneById(id: String = "GMT+07:00"): TimeZone {
    return TimeZone.getTimeZone(id)
}

val applicationTimeZone get() = getTimeZoneById()

fun dateFormatter(format: String = "yyyy-MM-dd"): SimpleDateFormat {
    val locale = Locale("id", "ID")

    return SimpleDateFormat(format, locale).apply {
        timeZone = applicationTimeZone
    }
}

fun getTimeZoneById2(id: String = "GMT+07:00"): TimeZone {
    return TimeZone.getTimeZone(id)
}

fun dateFormatter2(format: String = "yyyy-MM-dd"): SimpleDateFormat {
    return SimpleDateFormat(format, Locale("in_ID")).apply {
        timeZone = getTimeZoneById()
    }
}

fun getCurrentDate(): String  = dateFormatter("yyyy-MM-dd HH:mm:ss").format(Date())

val defaultDateFormat get() = dateFormatter()

val String.applicationDateFormatter: String
    get() =
        dateFormatter("dd-MM-yyyy HH:mm:ss").parse(this).let {
            dateFormatter("dd MMM yyyy").format(it ?: Date())
        }

fun String.formatDate(
    from: String = "dd-MM-yyyy HH:mm:ss",
    to: String = "dd MMM yyyy"
): String = dateFormatter(from).parse(this).let {
    dateFormatter(to).format(it ?: Date())
}

const val SECOND_MILLIS = 1000
const val MINUTE_MILLIS = 60 * SECOND_MILLIS
const val HOUR_MILLIS = 60 * MINUTE_MILLIS
const val DAY_MILLIS = 24 * HOUR_MILLIS
const val WEEK_MILLIS = 7 * DAY_MILLIS

fun String.getTimeAgo(originFormat : String) : String {
    val originDate = dateFormatter(originFormat).parse(this)
    var time = originDate.time
    if (time < 1000000000000L) {
        time *= 1000
    }

    val diff = Date().time - time
    Timber.e("diff $diff ")
    return when {
        diff < MINUTE_MILLIS -> "beberapa saat yang lalu"
        diff < 2 * MINUTE_MILLIS -> "1 menit yang lalu"
        diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} menit yang lalu"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} jam yang lalu"
        diff < 7 * DAY_MILLIS -> "${diff / DAY_MILLIS} hari yang lalu"
        diff/DAY_MILLIS < 30 -> "${diff / WEEK_MILLIS} minggu yang lalu"
        diff/DAY_MILLIS < 365 -> "${(diff / DAY_MILLIS)/30} bulan yang lalu"
        diff/DAY_MILLIS > 365 -> "${((diff / WEEK_MILLIS)/4)/12} tahun yang lalu"
        else -> "${diff / DAY_MILLIS} hari yang lalu (error)"
    }
}


