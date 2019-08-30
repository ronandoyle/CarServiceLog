package ie.nanorstudios.carservicelog.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ie.nanorstudios.carservicelog.activities.MainActivity

@Module
abstract class ActivityModule {
	@ContributesAndroidInjector(modules = [MainActivityModule::class])
	@ActivityScope
	abstract fun bindMainActivity(): MainActivity
}