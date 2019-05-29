package uk.co.jamiecruwys.apieceofcake.di

import dagger.Component
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)
}