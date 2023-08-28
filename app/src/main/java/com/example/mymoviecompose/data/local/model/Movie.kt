package com.example.mymoviecompose.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo("title")
    var title: String = "",
    @ColumnInfo("photo")
    var photoUrl: String = "",
)
