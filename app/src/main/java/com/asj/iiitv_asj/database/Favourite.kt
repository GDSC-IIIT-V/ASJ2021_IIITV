package com.asj.iiitv_asj.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    //id kept as primary key to give id to every row
    val id: Int = 0,
    //name column for the name of topics that are put into favs
    @ColumnInfo(name = "name")
    val favName : String,
    //isStarred column for favs starred(by default every fav ought to be starred in order to be in favs)
    @ColumnInfo(name = "starred")
    val isStarred : Boolean
)
