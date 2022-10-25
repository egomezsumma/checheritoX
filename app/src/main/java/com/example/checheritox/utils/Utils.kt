package com.example.checheritox.utils


import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * These functions create a formatted string that can be set in a TextView.
 */
private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

/**
 * Convert a duration to a formatted string for display.
 *
 * Examples:
 *
 * 6 seconds on Wednesday
 * 2 minutes on Monday
 * 40 hours on Thursday
 *
 * @param startTimeMilli the start of the interval
 * @param endTimeMilli the end of the interval
 * @param res resources used to load formatted strings
 */

//fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long): String {

    val durationMilli = endTimeMilli - startTimeMilli
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)

    val hours = TimeUnit.MILLISECONDS.toHours(durationMilli)%24

    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMilli)%60
    // long seconds = (milliseconds / 1000);
    val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMilli)%60

    if(hours>0){
        return "$hours:$minutes:$seconds"
    }
    else{
        return "$minutes:$seconds"
    }

    /*return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }*/
}

/**
 * Returns a string representing the numeric quality rating.

fun convertNumericQualityToString(quality: Int, resources: Resources): String {
var qualityString = resources.getString(R.string.three_ok)
when (quality) {
-1 -> qualityString = "--"
0 -> qualityString = resources.getString(R.string.zero_very_bad)
1 -> qualityString = resources.getString(R.string.one_poor)
2 -> qualityString = resources.getString(R.string.two_soso)
4 -> qualityString = resources.getString(R.string.four_pretty_good)
5 -> qualityString = resources.getString(R.string.five_excellent)
}
return qualityString
}*/


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long, showYear: Boolean =false): String {
    if(showYear){
        return SimpleDateFormat("EEEE dd MMM yyyy' 'HH:mm")
            .format(systemTime).toString()
    }
    return SimpleDateFormat("EEEE dd MMM' 'HH:mm")
        .format(systemTime).toString()
}

/**
 * Takes a list of SleepNights and converts and formats it into one string for display.
 *
 * For display in a TextView, we have to supply one string, and styles are per TextView, not
 * applicable per word. So, we build a formatted string using HTML. This is handy, but we will
 * learn a better way of displaying this data in a future lesson.
 *
 * @param   nights - List of all SleepNights in the database.
 * @param   resources - Resources object for all the resources defined for our app.
 *
 * @return  Spanned - An interface for text that has formatting attached to it.
 *           See: https://developer.android.com/reference/android/text/Spanned


fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
val sb = StringBuilder()
sb.apply {
append(resources.getString(R.string.title))
nights.forEach {
append("<br>")
append(resources.getString(R.string.start_time))
append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
if (it.endTimeMilli != it.startTimeMilli) {
append(resources.getString(R.string.end_time))
append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
append(resources.getString(R.string.quality))
append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
append(resources.getString(R.string.hours_slept))
// Hours
append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
// Minutes
append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
// Seconds
append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
}
}
}
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
} else {
return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}
}*/

/**
 * ViewHolder that holds a single [TextView].
 *
 * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
 * to the RecyclerView such as where on the screen it was last drawn during scrolling.
 */
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)


fun slugify(word: String, replacement: String = "-") = Normalizer
    .normalize(word, Normalizer.Form.NFD)
    .replace("[^\\p{ASCII}]".toRegex(), "")
    .replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim()
    .replace("\\s+".toRegex(), replacement)
    .toLowerCase()