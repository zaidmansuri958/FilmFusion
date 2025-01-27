package com.zaidmansuri.filmfusion.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zaidmansuri.filmfusion.model.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class LikeDataBase : RoomDatabase() {
    abstract fun likeDao(): LikeDao

    companion object {
        private var INSTANCE: LikeDataBase? = null
        fun getDatabase(context: Context): LikeDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,LikeDataBase::class.java, "like_db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}