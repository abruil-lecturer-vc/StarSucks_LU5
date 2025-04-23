package com.example.prog7313_starsucks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prog7313_starsucks.Product

@Database(entities =[Product::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDAO(): OrderDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): Any{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "order_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

/*
    Class provides the database instance and link the DAO
    Abstract (concrete) because it requires to extend RoomDatabase class (abstract)
    This class cannot be instantiated
 */