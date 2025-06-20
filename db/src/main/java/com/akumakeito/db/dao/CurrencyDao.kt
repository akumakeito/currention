package com.akumakeito.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.akumakeito.db.entity.FiatEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("UPDATE FiatEntity SET name_ru = :nameRu WHERE short_code = :shortCode")
    suspend fun updateCurrencyName(shortCode: String, nameRu: String)

    @Update
    suspend fun updateFavoriteCurrency(fiatEntity: FiatEntity)

    @Query(
        "SELECT * FROM FiatEntity WHERE isFavorite = 1"
    )
    suspend fun getFavoriteCurrencies(): List<FiatEntity>

    @Query(
        "SELECT * FROM FiatEntity WHERE isFavorite = 1"
    )
    fun getFavoriteCurrenciesFlow(): Flow<List<FiatEntity>>

    @Query(
        "SELECT * FROM FiatEntity WHERE short_code = :shortCode"
    )
    suspend fun getCurrencyByShortCode(shortCode: String): FiatEntity

    @Query(
        "UPDATE FiatEntity SET rates = :rates WHERE short_code = :shortCode"
    )
    suspend fun updateCurrencyRates(shortCode: String, rates: String)
}