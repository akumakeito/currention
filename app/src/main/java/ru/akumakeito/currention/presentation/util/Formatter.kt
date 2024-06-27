package ru.akumakeito.currention.presentation.util

fun Double.format(scale: Int) = "%.${scale}f".format(this)