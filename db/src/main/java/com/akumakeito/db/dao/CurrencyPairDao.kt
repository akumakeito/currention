package com.akumakeito.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akumakeito.db.entity.PairCurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyPairDao {

    @Query("SELECT * FROM pair_currency")
    fun getAllPairs(): Flow<List<PairCurrencyEntity>>

    @Query("SELECT * FROM pair_currency WHERE id = :id")
    suspend fun getPairById(id: Int): PairCurrencyEntity

    @Query("DELETE FROM pair_currency WHERE id = :id")
    suspend fun deletePairById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrencyPair(pairCurrency: PairCurrencyEntity)

    @Query("SELECT * FROM pair_currency ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedPair(): PairCurrencyEntity

}