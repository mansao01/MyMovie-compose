package com.example.mymoviecompose.navigation

sealed class Screen(val route:String) {
    object Home:Screen("home")
    object Search:Screen("search")
    object Favorite:Screen("favorite")
    object Detail:Screen("home/{movieId}"){
        fun createRoute(movieId:Int) = "home/$movieId"
    }
}