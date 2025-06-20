package com.akumakeito.core.models.domain

data class FiatCurrency(
    val nameEn: String,
    val nameRu: String,
    val shortCode: String,
    val code: String,
    val symbol: String,
    val flag: Int = 0,
    val isPopular: Boolean = false,
    val isFavorite: Boolean = false,
) {
    var rates : Map<String, Double> = emptyMap()
    var currentRate : Double? = null
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
