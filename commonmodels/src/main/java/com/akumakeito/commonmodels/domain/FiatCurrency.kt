package com.akumakeito.commonmodels.domain

data class FiatCurrency(
    val id: Int,
    val name: String,
    val shortCode: String,
    val code: String,
    val symbol: String,
    val flag: Int = 0,
    val isPopular: Boolean = false,
    val isFavorite: Boolean = false,
) {
    var rate : Double? = null
//    @SuppressLint("DiscouragedApi")
//    fun getStringResource( context: Context): String? {
//        val resourceId =
//            context.resources.getIdentifier("cur${this.shortCode.lowercase()}", "string", context.packageName)
//        return if (resourceId != 0) {
//            context.getString(resourceId)
//        } else {
//            null
//        }
//
//    }


}
