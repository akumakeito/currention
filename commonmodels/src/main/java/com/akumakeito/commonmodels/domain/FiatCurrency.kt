package com.akumakeito.commonmodels.domain

data class FiatCurrency(
    val id: Int,
    var name: String,
    val shortCode: String,
    val code: String,
    val symbol: String,
    var flag: Int = 0,
    var isPopular: Boolean = false,
    var isFavorite: Boolean = false
) {

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
