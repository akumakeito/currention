package com.akumakeito.commonui.presentation.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Double.format(scale: Int) = "%.${scale}f".format(this)

fun formatWithRange(double: Double): String {
    val symbols = DecimalFormatSymbols().apply { groupingSeparator = ' ' }
    val decimalFormat = DecimalFormat("#,##0.00", symbols)
    return decimalFormat.format(double)
}