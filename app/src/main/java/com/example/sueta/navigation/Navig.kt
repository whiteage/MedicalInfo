package com.example.sueta.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sueta.CreateMenu
import com.example.sueta.MainScreen
import com.example.sueta.MainVM


@Composable
fun Navig(viewModel : MainVM ) {
    val navHostController = rememberNavController()


    NavHost(navController = navHostController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(viewModel, navHostController)
        }

        composable(Screen.CreateScreen.route) {
            CreateMenu(viewModel, navHostController, "")
        }


        composable("creation/{cardName}") {

            val cardName = it.arguments?.getString("cardName")

            CreateMenu(viewModel, navHostController, cardName!!)
        }

    }
}
