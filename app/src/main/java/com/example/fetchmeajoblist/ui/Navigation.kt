package com.example.fetchmeajoblist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fetchmeajoblist.ui.hiringList.HiringListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigableScreen.HiringList.route) {
        composable(
            NavigableScreen.HiringList.route
        ) {
            HiringListScreen()
        }
    }
}
