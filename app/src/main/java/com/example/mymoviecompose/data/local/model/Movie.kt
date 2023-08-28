package com.example.mymoviecompose.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Movie(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("photo")
    val photoUrl: String,
    @ColumnInfo("isFavorite")
    val isFavorite: Boolean
)
