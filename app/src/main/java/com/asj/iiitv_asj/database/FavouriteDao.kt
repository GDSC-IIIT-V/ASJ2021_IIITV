package com.asj.iiitv_asj.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    //insert something into favs when starred
    @Insert
    suspend fun insert(favourite: Favourite)

    //delete specific fav when it is un-starred
    @Delete
    suspend fun delete(favourite: Favourite)

    //select all starred/favs from Favourite database
    @Query("SELECT * FROM favourite ORDER BY name ASC")
    fun getFavourites(): Flow<List<Favourite>>

}