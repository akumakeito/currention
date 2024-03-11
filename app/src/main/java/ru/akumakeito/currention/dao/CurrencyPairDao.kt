package ru.akumakeito.currention.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.http.GET
import ru.akumakeito.currention.entity.PairCurrencyEntity

@Dao
interface CurrencyPairDao {

    @Query("SELECT * FROM pair_currency")
    fun getAllPairs() : List<PairCurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrencyEntity)

}