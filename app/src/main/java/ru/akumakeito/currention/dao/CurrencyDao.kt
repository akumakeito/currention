package ru.akumakeito.currention.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.akumakeito.currention.entity.FiatEntity

@Dao
interface CurrencyDao {
    @Query("SELECT COUNT(*)==0 FROM FiatEntity")
    suspend fun isEmpty() : Boolean

    @Query("SELECT * FROM FiatEntity" )
    suspend fun getAllFiat() : List<FiatEntity>

    @Insert
    suspend fun insertAllFiat(fiatList : List<FiatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiat(fiat : FiatEntity)

    @Query("DELETE FROM FiatEntity")
    suspend fun deleteAllFiat()

    @Query("UPDATE FiatEntity SET flag = :flag WHERE short_code = :shortCode")
    suspend fun updateFlagByShortCode(shortCode : String, flag : Int)
}