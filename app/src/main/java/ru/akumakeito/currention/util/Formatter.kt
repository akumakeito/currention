package ru.akumakeito.currention.util

fun Double.format(scale: Int) = "%.${scale}f".format(this)