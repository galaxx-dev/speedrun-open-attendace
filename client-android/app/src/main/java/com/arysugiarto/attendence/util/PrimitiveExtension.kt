package com.arysugiarto.attendence.util

import android.os.Build
import android.text.Html
import android.util.Patterns
import androidx.core.text.buildSpannedString
import com.arysugiarto.attendence.util.Const.PRODUCT.NOT_AVAILABLE
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

const val emptyString = ""
const val emptyInt = -1
const val emptyFloat = 0F
const val emptyLong = 0L
const val emptyDouble = 0.0
const val emptyBoolean = false

/**
 * Proceed and Return Non-Null Value of Nullable String
 * @param default is the replacement of nullable value of any word in the string
 * @param condition is the condition which nullable can be replaced with non-null value
 * @return [String]
 */
fun String?.orEmpty(
    default: String = emptyString,
    condition: Regex? = null
): String {
    val regex = condition ?: Regex("^(null|NULL|Null)")
    return if(this?.contains(regex) == true) replace(regex, default) else this ?: default
}

/**
 * Proceed and Return Non-Null Value of Nullable String
 * @return [String]
 */
val String?.orEmpty get() = orEmpty(emptyString)

/**
 * Check if string is match for Regular Email Pattern
 * @return [Boolean], if Match and Not Null will return True otherwise false
 */
fun String?.isEmailPattern() = Patterns.EMAIL_ADDRESS.matcher(orEmpty).matches()

/**
 * Check if string is match for Numeric Pattern
 * @return [Boolean], if Match and Not Null will return True otherwise false
 */
fun String?.isNumeric() = orEmpty.toLongOrNull() != null

/**
 * Check if string is match of Alphabet
 * @return [Boolean], if Match and Not Null will return True otherwise false
 */
fun String?.isAlphabet() = orEmpty.matches(Regex("[a-zA-Z]+"))

/**
 * Check if string is match of Alphabet & Space
 * @return [Boolean], if Match and Not Null will return True otherwise false
 */
fun String?.isAlphabetSpace() = orEmpty.matches(Regex("[a-zA-Z\\s]+"))

/**
 * Check if string is contains right Phone Number Pattern such as ID-Prefix and ID-length
 * @return [Boolean], if Match and Not Null will return True otherwise false
 */
fun String?.isPhoneNumber() = isNumeric() &&
        ((orEmpty.startsWith("08") && orEmpty.length == 12) ||
                (orEmpty.startsWith("62") && orEmpty.length == 13) ||
                (orEmpty.startsWith("+62") && orEmpty.length == 14))

fun String?.containWhiteSpace(): Boolean {
    if (this == null) return true
    var character = 0
    while (character < this.length) {
        if (!Character.isWhitespace(this[character])) return false
        ++character
    }
    return true
}

val String?.orNotAvailable get() =
    if (this == null || this == emptyString || this == 0.toString()) NOT_AVAILABLE else this



val String.asApplicationLowerCase get() = toLowerCase(Locale.ROOT)

fun String.strike(range: Pair<Int, Int> = Pair(0, 0)) = buildSpannedString {
    val lastRange = if (range.second == 0) length else range.second
    append(this, range.first, lastRange)
}

/**
 * Proceed and Return Non-Null Value of Nullable CharSequence
 * @param default is the replacement of nullable value of any word in the CharSequence
 * @param condition is the condition which nullable can be replaced with non-null value
 * @return [CharSequence]
 */
fun CharSequence?.orEmpty(
    default: String = emptyString,
    condition: Regex? = null
): CharSequence {
    val regex = condition ?: Regex("^(null|NULL|Null)")
    return if(this?.contains(regex) == true) replace(regex, default) else this ?: default
}

/**
 * Proceed and Return Non-Null Value of Nullable CharSequence
 * @return [CharSequence]
 */
val CharSequence?.orEmpty get() = orEmpty(emptyString)

/**
 * Proceed and Return Non-Null Value of Nullable Long
 * @param default is the replacement of nullable value of Long
 * @return [Long]
 */
fun Long?.orEmpty(default: Long = emptyLong) = this ?: default

val Long?.orNotAvailable: String get() =
    if (this == null || this == emptyLong) NOT_AVAILABLE else this.toString()

/**
 * Proceed and Return Non-Null Value of Nullable Long
 * @return [Long]
 */
val Long?.orEmpty get() = orEmpty(emptyLong)

/**
 * Proceed and Return Non-Null Value of Nullable Float
 * @param default is the replacement of nullable value of Float
 * @return [Float]
 */
fun Float?.orEmpty(default: Float = emptyFloat) = this ?: default

/**
 * Proceed and Return Non-Null Value of Nullable Float
 * @return [Float]
 */
val Float?.orEmpty get() = orEmpty(emptyFloat)

val Float?.orNotAvailable: String get() =
    if (this == null || this == emptyFloat) NOT_AVAILABLE else this.toString()

/**
 * Proceed and Return Non-Null Value of Nullable Int
 * @param default is the replacement of nullable value of Int
 * @return [Int]
 */
fun Int?.orEmpty(default: Int = emptyInt) = this ?: default

/**
 * Proceed and Return Non-Null Value of Nullable Int
 * @return [Int]
 */
val Int?.orEmpty get() = orEmpty()

val Int?.orNotAvailable: String get() =
    if (this == null || this == emptyInt) NOT_AVAILABLE else this.toString()

/**
 * Format Int to ID Currency Value of String Type
 * @param prefix used for prefix of currency Value with default ID currency (Rp.)
 * @param suffix used for suffix of currency value
 * @return [String] Of ID Currency
 */
fun Int?.asIDCurrency(
    prefix: String = "Rp",
    suffix: String = ""
) : String {
    val currencyFormat: NumberFormat = DecimalFormat("#,###")
    val amount = if (orEmpty > 0) this else 0
    return "${prefix}${currencyFormat.format(amount).orEmpty()
        .replace(',', '.')} $suffix"
}

/**
 * Proceed and Return Non-Null Value of Nullable Double
 * @param default is the replacement of nullable value of Double
 * @return [Double]
 */
fun Double?.orEmpty(default: Double = emptyDouble) = this ?: default

/**
 * Proceed and Return Non-Null Value of Nullable Double
 * @return [Double]
 */
val Double?.orEmpty get() = orEmpty(emptyDouble)

val Double?.orNotAvailable: String get() =
    if (this == null || this == emptyDouble) NOT_AVAILABLE else this.toString()

/**
 * Proceed and Return Non-Null Value of Nullable Boolean
 * @param default is the replacement of nullable value of Boolean
 * @return [Boolean]
 */
fun Boolean?.orEmpty(default: Boolean = emptyBoolean) = this ?: default

/**
 * Proceed and Return Non-Null Value of Nullable Boolean
 * @return [Boolean]
 */
val Boolean?.orEmpty get() = orEmpty()

/**
 * Proceed and return true if password is validate with min 6 characters, include min 1 uppercase and 1 lowercase
 * @return [Boolean]
 */
fun String?.isPasswordValidate() : Boolean{
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]\\S{6,}\$")
    return this?.contains(regex) == true
}

/**
 * @param prefix is for the currency format, default on RP
 * @param suffix is written on the end of price
 * **/

fun Int?.currencyFormat(
    prefix: String = "Rp",
    suffix: String = ""
) : String {
    val currencyFormat: NumberFormat = DecimalFormat("#,###")
    return try {
        val currency = if (orEmpty > 0) this else 0

        "${prefix}${currencyFormat.format(currency).toString()
            .replace(',', '.')} $suffix"
    } catch (e: Exception) {
        "$prefix 0"
    }
}

val Int?.currencyFormat get() = currencyFormat()

fun Int?.pointFormat(
) : String {
    val currencyFormat: NumberFormat = DecimalFormat("#,###")
    return try {
        currencyFormat.format(this).toString()
            .replace(',', '.')
    } catch (e: Exception) {
        "0"
    }
}

val Int?.pointFormat get() = pointFormat()

fun String.htmlToString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this).toString()
    }
}


















