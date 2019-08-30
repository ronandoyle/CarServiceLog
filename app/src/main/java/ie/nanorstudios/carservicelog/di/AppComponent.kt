package ie.nanorstudios.carservicelog.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ie.nanorstudios.carservicelog.CSLApp
import ie.nanorstudios.carservicelog.di.activitymodules.ActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class,
	RxModule::class])
interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		fun app(app: Application): Builder
		fun build(): AppComponent
	}

	fun inject(app: CSLApp)
}