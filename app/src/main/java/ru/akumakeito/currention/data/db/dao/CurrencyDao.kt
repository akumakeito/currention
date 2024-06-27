package ru.akumakeito.currention.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.akumakeito.currention.data.db.entity.FiatEntity

@Dao
interface CurrencyDao {
    @Query("SELECT COUNT(*)==0 FROM FiatEntity")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * FROM FiatEntity")
    fun getAllFiat(): Flow<List<FiatEntity>>

    @Insert
    suspend fun insertAllFiat(fiatList: List<FiatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiat(fiat: FiatEntity)

    @Query("DELETE FROM FiatEntity")
    suspend fun deleteAllFiat()

    @Query("UPDATE FiatEntity SET flag = :flag WHERE short_code = :shortCode")
    suspend fun updateFlagByShortCode(shortCode: String, flag: Int)

    @Query("UPDATE FiatEntity SET isPopular = 1 WHERE short_code = :shortCode")
    suspend fun updateCurrencyPopularityByShortCode(shortCode: String)

    @Query("UPDATE FiatEntity SET name = :name WHERE short_code = :shortCode")
    suspend fun updateCurrencyName(shortCode: String, name: String)

    @Query(
        "UPDATE fiatentity SET \n" +
                "isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END WHERE id = :id"
    )
    suspend fun updateFavoriteCurrency(id: Int)
}