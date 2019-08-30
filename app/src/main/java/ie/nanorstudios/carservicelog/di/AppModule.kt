package ie.nanorstudios.carservicelog.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {
	@Singleton
	@Provides
	fun provideContext(app: Application): Context = app.applicationContext
}