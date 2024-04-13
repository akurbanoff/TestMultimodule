package ru.axas.testmultimodule

import android.app.Activity
import android.app.Application
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.axas.auction.di.AuctionDepsStore
//import ru.axas.testmultimodule.di.AppComponent
//import ru.axas.testmultimodule.di.DaggerAppComponent
import javax.inject.Inject
import javax.inject.Scope

class App : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}