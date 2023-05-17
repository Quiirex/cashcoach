package com.tva.cashcoach.appcomponents.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tva.cashcoach.appcomponents.model.category.Category
import com.tva.cashcoach.appcomponents.model.expense.Expense
import com.tva.cashcoach.appcomponents.model.income.Income
import com.tva.cashcoach.appcomponents.model.user.User
import com.tva.cashcoach.appcomponents.model.user.UserDao
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.utility.Converters

@Database(
    entities = [User::class, Wallet::class, Expense::class, Income::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("AppDatabase", "getDatabase")
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