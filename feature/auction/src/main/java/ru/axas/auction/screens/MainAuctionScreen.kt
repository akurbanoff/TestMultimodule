package ru.axas.auction.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.axas.auction.di.AuctionComponent
import ru.axas.auction.di.AuctionDepsStore
import ru.axas.auction.di.AuctionViewModelComponent
import ru.axas.core.base.extension.LifeScreen
import ru.axas.core.base.extension.daggerViewModel
import javax.inject.Inject

@Composable
fun MainAuctionScreen(
    goToNotification: () -> Unit
) {
    val auctionComponent = AuctionViewModelComponent().auctionComponent
    val viewModel = auctionComponent.auctionViewModel.create()

    viewModel.log()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Auction Screen")
        Button(onClick = goToNotification) {
            Text(text = "go to notification")
        }
    }
}
