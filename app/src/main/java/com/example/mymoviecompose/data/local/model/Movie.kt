package com.example.mymoviecompose.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("photo")
    val photoUrl: String,
    @ColumnInfo("isFavorite")
    val isFavorite: Boolean
)
