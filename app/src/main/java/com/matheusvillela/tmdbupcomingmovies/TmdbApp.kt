package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

open class TmdbApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
        MemberInjectorRegistryLocator.setRootRegistry(com.matheusvillela.tmdbupcomingmovies.MemberInjectorRegistry())
        FactoryRegistryLocator.setRootRegistry(com.matheusvillela.tmdbupcomingmovies.FactoryRegistry())

        val appScope: Scope = Toothpick.openScope(this)

        appScope.installModules(AppModule(this))
        Toothpick.inject(this, appScope)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}