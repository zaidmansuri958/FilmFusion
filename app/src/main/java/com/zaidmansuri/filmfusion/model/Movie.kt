package com.zaidmansuri.filmfusion.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likesTable")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val image:String?=null,
    val title:String?=null,
    val video:String?=null,
    val desc:String?=null,
)
