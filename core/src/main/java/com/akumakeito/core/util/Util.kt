package com.akumakeito.core.util

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.akumakeito.core.models.ErrorType
import java.io.IOException
import java.net.UnknownHostException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun getErrorType(e: Throwable?): ErrorType = when (e) {
    is UnknownHostException -> ErrorType.SERVER
    is HttpException -> ErrorType.SERVER
    is IOException -> ErrorType.NETWORK
    else -> ErrorType.UNKNOWN
}