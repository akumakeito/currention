package ru.akumakeito.currention.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.akumakeito.currention.entity.FiatEntity
import ru.akumakeito.currention.dto.FiatCurrency

@Dao
interface CurrencyDao {
    @Query("SELECT COUNT(*)==0 FROM FiatEntity")
    suspend fun isEmpty() : Boolean

    @Query("SELECT * FROM FiatEntity" )
    suspend fun getAllFiat() : List<FiatEntity>

    @Insert
    suspend fun insertAllFiat(fiatList : List<FiatEntity>)
}