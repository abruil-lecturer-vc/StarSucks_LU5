package com.example.prog7313_starsucks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/*
    Interface class defines the method signatures for accessing the db
    Room automatically generates the implementation of OrderDAO when
    building the database
    No need for additional logic
 */
@Dao
interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM product_table")
    fun getAllOrders(): List<Product>
}