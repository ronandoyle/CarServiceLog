package ie.nanorstudios.carservicelog.di.activitymodules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ie.nanorstudios.carservicelog.activities.MainActivity
import ie.nanorstudios.carservicelog.di.ActivityScope

@Module
abstract class ActivityModule {
	@ContributesAndroidInjector(modules = [MainActivityModule::class])
	@ActivityScope
	abstract fun bindMainActivity(): MainActivity
}