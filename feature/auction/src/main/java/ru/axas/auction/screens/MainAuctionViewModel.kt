package ru.axas.auction.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.axas.auction.api.RealtyApi
import javax.inject.Inject
import javax.inject.Provider

class MainAuctionViewModel @AssistedInject constructor(
    private val realtyApi: RealtyApi
) : ViewModel(){

        fun log() = viewModelScope.launch{
            Log.d("CHECK_LOG", realtyApi::class.toString())
        }
    @AssistedFactory
    interface Factory{
        fun create(): MainAuctionViewModel
    }
}