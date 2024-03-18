package ru.akumakeito.currention.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.akumakeito.currention.entity.PairCurrencyEntity

@Dao
interface CurrencyPairDao {

    @Query("SELECT * FROM pair_currency")
    fun getAllPairs() : Flow<List<PairCurrencyEntity>>

    @Query("SELECT * FROM pair_currency WHERE id = :id")
    fun getPairById(id : Int) : PairCurrencyEntity

    @Query("DELETE FROM pair_currency WHERE id = :id")
    fun deletePairById(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrencyEntity)

    @Query("SELECT * FROM pair_currency ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedPair() : PairCurrencyEntity

}