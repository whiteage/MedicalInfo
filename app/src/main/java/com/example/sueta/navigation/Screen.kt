package com.example.sueta.navigation

sealed class Screen (val route : String)  {
    object MainScreen : Screen("main")
    object CreateScreen : Screen("create"){
        fun cardNamee(cardName : String) = "/$cardName"
    }


}