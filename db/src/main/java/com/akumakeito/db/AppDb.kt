package com.akumakeito.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akumakeito.db.dao.CurrencyDao
import com.akumakeito.db.dao.CurrencyPairDao
import com.akumakeito.db.entity.FiatEntity
import com.akumakeito.db.entity.PairCurrencyEntity


@Database(
    entities = [FiatEntity::class, PairCurrencyEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun provideCurrencyDao(): CurrencyDao

    abstract fun provideCurrencyPairDao(): CurrencyPairDao
}