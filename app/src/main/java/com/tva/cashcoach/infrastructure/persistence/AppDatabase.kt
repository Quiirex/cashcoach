package com.tva.cashcoach.infrastructure.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tva.cashcoach.infrastructure.model.category.Category
import com.tva.cashcoach.infrastructure.model.category.CategoryDao
import com.tva.cashcoach.infrastructure.model.transaction.Transaction
import com.tva.cashcoach.infrastructure.model.transaction.TransactionDao
import com.tva.cashcoach.infrastructure.model.user.User
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.model.wallet.Wallet
import com.tva.cashcoach.infrastructure.model.wallet.WalletDao
import com.tva.cashcoach.infrastructure.utility.Converters

@Database(
    entities = [User::class, Wallet::class, Category::class, Transaction::class],
    version = 13,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getWalletDao(): WalletDao

    abstract fun getTransactionDao(): TransactionDao

    abstract fun getCategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}