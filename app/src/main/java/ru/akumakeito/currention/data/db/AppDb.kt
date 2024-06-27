package ru.akumakeito.currention.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.akumakeito.currention.data.db.dao.CurrencyDao
import ru.akumakeito.currention.data.db.dao.CurrencyPairDao
import ru.akumakeito.currention.data.db.entity.FiatEntity
import ru.akumakeito.currention.data.db.entity.PairCurrencyEntity


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