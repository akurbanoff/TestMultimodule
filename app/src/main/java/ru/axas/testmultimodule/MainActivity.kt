package ru.axas.testmultimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.axas.auction.AuctionGraph
import ru.axas.core.theme.TestMultimoduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestMultimoduleTheme {
                RootGraph()
            }
        }
    }
}

@Composable
fun RootGraph() {
    val navController = rememberNavController()

    // Main Graph
    Scaffold(
        bottomBar = {}
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = "auction_graph"
        ){
            composable("auction_graph"){
                AuctionGraph(rootNavController = navController)
            }
            composable("notification_graph"){
                val navC = rememberNavController()
                NavHost(navController = navC, startDestination = "nt"){
                    composable("nt"){
                        Column {
                            Text(text = "Notification")
                        }
                    }
                }
            }
        }
    }
}