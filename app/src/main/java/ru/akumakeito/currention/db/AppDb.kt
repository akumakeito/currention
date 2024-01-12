package ru.akumakeito.currention.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.Provides
import ru.akumakeito.currention.dao.CurrencyDao
import ru.akumakeito.currention.entity.FiatEntity


@Database(entities = [FiatEntity::class], version = 4, exportSchema = false)
abstract class AppDb : RoomDatabase() {

    abstract fun provideCurrencyDao() : CurrencyDao
}