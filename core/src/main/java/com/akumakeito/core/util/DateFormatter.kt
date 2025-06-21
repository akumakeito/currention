package com.akumakeito.core.util

import android.content.res.Resources
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun Long.toDateTimeString(): String {
    val timeZone = ZoneId.systemDefault()
    val locale = Resources.getSystem().configuration.locales.get(0)
    val instant = Instant.ofEpochMilli(this)
    val zonedTime = instant.atZone(timeZone)

    val formatterStyle =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale)

    return zonedTime.format(formatterStyle)
}