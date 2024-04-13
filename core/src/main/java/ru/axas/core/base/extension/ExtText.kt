package ru.axas.core.base.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale


fun TextFieldValue.getFormattedNumberRu(): TextFieldValue {
    var answer = text.onlyDigit()
    val lengthChange = text.length - answer.length
    var rangeStart = selection.start
    var rangeEnd = selection.end
    var outF = ""

    return try {
        if (answer.isNotEmpty() && ('7' == answer[0] || '8' == answer[0] || '9' == answer[0]) && answer.length < 12) {
            if (answer[0] == '9') answer = "7$answer"
            if (answer[0] == '8') answer = "7${answer.substring(1)}"
            outF += "+${answer[0]}"
            if (answer.length > 1) outF += " (" + answer.substring(1, 4.coerceAtMost(answer.length))
            if (answer.length >= 5) outF += ") " + answer.substring(4,
                7.coerceAtMost(answer.length))
            if (answer.length >= 8) outF += "-" + answer.substring(7, 9.coerceAtMost(answer.length))
            if (answer.length >= 10) outF += "-" + answer.substring(9, answer.length)
        } else if (answer.isNotEmpty()) {
            outF += "+$answer"
        }
        when {
            lengthChange == 0 || (lengthChange == 1 && rangeStart == 12) -> {
                rangeStart += outF.length
                rangeEnd += outF.length
            }
            lengthChange == 1 && rangeStart == 3 -> {
                rangeStart += outF.length
                rangeEnd += outF.length
            }
            selection.end == text.length -> {
                rangeStart += lengthChange
                rangeEnd += lengthChange
            }
        }
        TextFieldValue(text = outF, selection = TextRange(rangeStart, rangeEnd), this.composition)
    } catch (e: Exception) {
        this
    }
}

val LocaleRu = Locale("ru", "RU")

fun TextFieldValue.getFormattedNumberPt(): TextFieldValue {
    val answer = text.onlyDigit()
    val lengthChange = text.length - answer.length
    var rangeStart = selection.start
    var rangeEnd = selection.end
    var outF = ""
    return try {
        if (answer.length in 4..12) {
            outF += "+${answer.substring(0, 3.coerceAtMost(answer.length))}" +
                    " ${answer.substring(3, 6.coerceAtMost(answer.length))}"
            if (answer.length > 6) outF += " ${answer.substring(6, 9.coerceAtMost(answer.length))}"
            if (answer.length > 9) outF += " ${answer.substring(9, answer.length)}"
        } else if (answer.isNotEmpty()) {
            outF += "+$answer"
        }
        when {
            lengthChange == 0 -> {
                rangeStart += outF.length
                rangeEnd += outF.length
            }
            lengthChange == 1 && rangeStart == 13 -> {
                rangeStart += outF.length
                rangeEnd += outF.length
            }
            this.selection.end == text.length -> {
                rangeStart += lengthChange
                rangeEnd += lengthChange
            }
        }
        TextFieldValue(text = outF, selection = TextRange(rangeStart, rangeEnd), composition)
    } catch (e: Exception) {
        this
    }
}

fun String.isValidWebAddress(): Boolean {
    val regex = Regex("^https?://(?:www\\.)?[\\w.-]+(?:\\.[a-zA-Z]{2,})+[/\\w.-]*$")

    return regex.matches(this)
}

fun String.extractDomain(): String? {
    val regex = Regex("^https?://(?:www\\.)?([\\w.-]+\\.[a-zA-Z]{2,})")

    val matchResult = regex.find(this)
    return matchResult?.groupValues?.get(1)
}

fun String.createUrl(): String {
    val prefix = "http://"
    val postfix = "/"
    return prefix + this.trimEnd('/') + postfix
}

fun String.onlyDigit()= Regex("[^0-9]").replace(this, "")

fun String.formattedNumberPhoneRu(): String {
    var phone =  this
    var outF = ""
    return try {
        if (('7' == phone[0] || '8' == phone[0] || '9' == phone[0]) && phone.length < 12) {
            if (phone[0] == '9') phone = "7$this"
            if (phone[0] == '8') phone = "7${this.substring(1)}"
            outF += "+${this[0]}"
            if (phone.length > 1) outF += " (" + phone.substring(1, 4.coerceAtMost(phone.length))
            if (phone.length >= 5) outF += ") " + phone.substring(4, 7.coerceAtMost(phone.length))
            if (phone.length >= 8) outF += "-" + phone.substring(7, 9.coerceAtMost(phone.length))
            if (phone.length >= 10) outF += "-" + phone.substring(9, phone.length)
        } else if (phone.isNotEmpty()) {
            outF += "+$phone"
        }
        outF
    } catch (e: Exception) {
        this
    }
}

fun String.formattedNumberPhonePt(): String {
    val phone =  this.onlyDigit()
    var outF = ""
    return try {
        if (phone.length in 3..12) {
            outF += "+${phone.substring(0, 3.coerceAtMost(phone.length))}" +
                    " ${phone.substring(3, 6.coerceAtMost(phone.length))}"
            if (phone.length >= 6) outF += " ${phone.substring(6, 9.coerceAtMost(phone.length))}"
            if (phone.length >= 9) outF += " ${phone.substring(9, phone.length)}"
        } else if (phone.isNotEmpty()) {
            outF += "+$phone"
        }
        outF
    } catch (e: Exception) {
        this
    }
}

fun Long.formatTimeElapsed(): String {
    val currentUnixTime = Instant.now().epochSecond
    val elapsedTime = currentUnixTime - this

    return when {
        elapsedTime < 60 -> "в сети"
        elapsedTime < 3600 -> {
            val minutes = elapsedTime / 60
            "$minutes минут назад"
        }
        elapsedTime < 86400 -> {
            val hours = elapsedTime / 3600
            "$hours часов назад"
        }
        elapsedTime < 172800 -> "вчера"
        else -> {
//            val date = Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).toLocalDate()
            val date = SimpleDateFormat("dd.MM.yyyy", LocaleRu).format(this)
            date.toString()
        }
    }
}

/*
fun main() {
    val startTimeUnix = 1632048000L // Unix timestamp для начального времени
    val endTimeUnix = 1633456800L // Unix timestamp для конечного времени

    val difference = calculateTimeDifference(startTimeUnix, endTimeUnix)
    println("Разница: $difference")
}
*/

fun calculateTimeDifference(startTimeUnix: Long, endTimeUnix: Long): String {
    val startTime = Instant.ofEpochSecond(startTimeUnix)
    val endTime = Instant.ofEpochSecond(endTimeUnix)

    val duration = Duration.between(startTime, endTime)
    val days = duration.toDays()
    val weeks = days / 7

    return when {
        weeks >= 2 -> "за $weeks недели"
        days >= 1 -> "за $days дней"
        else -> "меньше одного дня"
    }
}

fun Int?.roundIntForeRubOld():Double{
    this ?: return 0.00
    val bd = BigDecimal(this * 0.01)
    val roundOff = bd.setScale(2, RoundingMode.CEILING)
    val twoDForm = DecimalFormat("#.##")
    val dfs = DecimalFormatSymbols()
    dfs.decimalSeparator = '.'
    twoDForm.decimalFormatSymbols = dfs
    return  twoDForm.format(roundOff.toDouble()).toDouble()
}


fun Long.getYer(): Int = SimpleDateFormat("yyyy", LocaleRu).format(this).toIntOrNull() ?: 0
fun Long.millDateDDMMYYYY(): String = SimpleDateFormat("dd.MM.yyyy", LocaleRuTime).format(this).orEmpty()
fun String.fromStringToUnixLong(): Long? = SimpleDateFormat("dd.MM.yyyy", LocaleRuTime).parse(this)?.time?.div(1000)
fun Long.getYear(): Int = SimpleDateFormat("yyyy", LocaleRu).format(this).toIntOrNull() ?: 0
fun Long.getMonth(): Int = SimpleDateFormat("MM", LocaleRu).format(this).toIntOrNull() ?: 0
fun Long.getDay(): Int = SimpleDateFormat("dd", LocaleRu).format(this).toIntOrNull() ?: 0
fun LocalDate.dateForMillis(): Long = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun Long.toDateString(): String = SimpleDateFormat("dd.MM.yyyy", LocaleRu).format(this)
fun Long.unixToDateTimeString(): String = SimpleDateFormat("dd.MM.yyyy HH:mm", LocaleRu).format(this)
fun Int.unixToDateString(): String = SimpleDateFormat("dd.MM.yyyy", LocaleRu).format(this * 1000L)
fun Int.unixToDateTimeString(): String = SimpleDateFormat("dd.MM.yyyy HH:mm", LocaleRu).format(this * 1000L)
fun Int.unixToTimeString(): String = SimpleDateFormat("HH:mm", LocaleRu).format(this * 1000L)
fun Int.getYear(): Int = SimpleDateFormat("yyyy", LocaleRu).format(this * 1000L).toInt()
fun Int.getMonth(): Int = SimpleDateFormat("MM", LocaleRu).format(this * 1000L).toInt()
fun Int.getDay(): Int = SimpleDateFormat("dd", LocaleRu).format(this * 1000L).toInt()
fun Int.unixToDateMMMMString(): String = SimpleDateFormat("MMMM yyyy", LocaleRu).format(this * 1000L)
fun Int.unixToDateDDMMMMString(): String = SimpleDateFormat("dd MMMM", LocaleRu).format(this * 1000L)


fun String.toWords() = trim().splitToSequence(' ').filter { it.isNotEmpty() }.toList()

fun Date.toTimeStringForeFile(): String =
        SimpleDateFormat("yyyyMMdd_HHmmss", LocaleRu).format(this)

fun Long.toTimeStringForeFile(): String =
        SimpleDateFormat("yyyyMMdd_HHmmss", LocaleRu).format(this)

fun getHourNow(): Int = LocalTime.now().hour
fun getMinuteNow(): Int = LocalTime.now().minute
fun timeNow() = System.currentTimeMillis()
fun timeNowUnix() = System.currentTimeMillis().div(1000).toInt()
fun Long.toUnixSeconds() = this.div(1000).toInt()

fun formattingMembers(count: Int): String {
    val devHun = count % 100
    val devTen = devHun % 10

    return when {
        devHun in 11..19 -> "$count участников"
        devTen == 1 -> "$count участник"
        devTen in 2..4 -> "$count участника"
        else -> "$count участников"
    }
}

fun String.toUpperCaseFirstChar() = replaceFirstChar(Char::titlecase)

private val LocaleRuTime = Locale("ru", "RU")