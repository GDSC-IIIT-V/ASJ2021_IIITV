package com.asj.iiitv_asj

import android.app.Application
import com.asj.iiitv_asj.database.FavouriteRoomDatabase

//rename the Application as required {e.g. FavouriteApplication etc.}
class CourseApplication : Application() { //application class to get database managed

    val database: FavouriteRoomDatabase by lazy { FavouriteRoomDatabase.getDatabase(this) }
}