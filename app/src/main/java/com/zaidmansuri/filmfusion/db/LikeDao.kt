package com.zaidmansuri.filmfusion.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaidmansuri.filmfusion.model.Movie

@Dao
interface LikeDao {
    @Query("SELECT * FROM likesTable")
    fun getLikes(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(likeRequest: Movie)

    @Query("SELECT EXISTS(SELECT * FROM likesTable Where image=:img)")
    suspend fun likedOrNot(img:String):Boolean

    @Query("DELETE FROM likesTable Where image=:img")
    suspend fun removeLike(img: String)

}