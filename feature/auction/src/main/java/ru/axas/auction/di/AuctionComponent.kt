package ru.axas.auction.di

import android.app.Application
import androidx.annotation.RestrictTo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.axas.api.NetworkModule
import ru.axas.auction.api.RealtyApi
import ru.axas.auction.di.modules.AuctionApiModule
import ru.axas.auction.screens.MainAuctionViewModel
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

@Component(modules = [NetworkModule::class, AuctionApiModule::class])
@Singleton
internal interface AuctionComponent: AuctionDeps {

    override val auctionViewModel: MainAuctionViewModel.Factory


    @Component.Builder
    interface Builder{
        fun build(): AuctionComponent
    }
}

interface AuctionDeps {
    val auctionViewModel: MainAuctionViewModel.Factory
}

interface AuctionDepsProvider{
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: AuctionDeps

    companion object: AuctionDepsProvider by AuctionDepsStore
}

object AuctionDepsStore: AuctionDepsProvider{
    override var deps: AuctionDeps by notNull()
}

internal class AuctionViewModelComponent: ViewModel() {
    val auctionComponent = DaggerAuctionComponent.builder().build()
}
