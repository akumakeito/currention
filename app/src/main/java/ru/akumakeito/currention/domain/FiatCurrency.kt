package ru.akumakeito.currention.domain

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

    fun doesMatchSearchQuery(query: String): Boolean {

        val machingCombinations = listOf(
            "$name",
            "$name $shortCode",
            "$name$shortCode",
            "$shortCode",
            "$shortCode $name",
            "$shortCode$name"
        )

        return machingCombinations.any { it.contains(query, ignoreCase = true) }
    }

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
