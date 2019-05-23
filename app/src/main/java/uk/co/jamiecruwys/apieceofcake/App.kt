package uk.co.jamiecruwys.apieceofcake

import android.app.Application
import uk.co.jamiecruwys.apieceofcake.di.ApiModule
import uk.co.jamiecruwys.apieceofcake.di.AppComponent
import uk.co.jamiecruwys.apieceofcake.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule("https://gist.githubusercontent.com/t-reed/"))
            .build()

        appComponent.inject(this)
    }
}