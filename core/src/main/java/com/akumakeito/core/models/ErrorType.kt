package com.akumakeito.core.models

import androidx.annotation.StringRes
import com.akumakeito.commonres.R

enum class ErrorType(@StringRes val message: Int) {
    NETWORK(R.string.error_network),
    SERVER(R.string.error_server),
    UNKNOWN(R.string.error_unknown)
}