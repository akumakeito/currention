package ru.akumakeito.currention.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Provides
import ru.akumakeito.currention.dao.CurrencyDao
import ru.akumakeito.currention.entity.FiatEntity
import ru.akumakeito.currention.entity.PairCurrencyEntity


@Database(entities = [FiatEntity::class, PairCurrencyEntity::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun provideCurrencyDao() : CurrencyDao
}