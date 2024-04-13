package ru.axas.auction.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.axas.auction.api.RealtyApi
import javax.inject.Singleton

@Module
class AuctionApiModule {
    @[Singleton Provides]
    fun provideRealtyApi(retrofit: Retrofit): RealtyApi = retrofit.create(RealtyApi::class.java)
}