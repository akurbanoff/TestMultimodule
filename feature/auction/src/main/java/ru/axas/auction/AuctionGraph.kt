package ru.axas.auction

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.axas.auction.screens.MainAuctionScreen

@Composable
fun AuctionGraph(rootNavController: NavHostController) {
    val navController = rememberNavController()

    val goToNotification: () -> Unit = remember{{
        navController.navigate("nt")
    }}

    NavHost(
        navController = navController,
        startDestination = "auction"
    ){
        composable("auction"){
            MainAuctionScreen(goToNotification)
        }

        composable("notification"){
            navController.clearBackStack("auction")
            navController.popBackStack()
            rootNavController.navigate("notification_graph")
        }
    }
}