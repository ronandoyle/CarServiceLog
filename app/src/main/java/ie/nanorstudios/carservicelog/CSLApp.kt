package ie.nanorstudios.carservicelog

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import ie.nanorstudios.carservicelog.di.AppComponent
import ie.nanorstudios.carservicelog.di.DaggerAppComponent
import javax.inject.Inject

class CSLApp: Application(), HasActivityInjector, HasSupportFragmentInjector {

	private lateinit var instance: CSLApp
	@Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
	@Inject lateinit var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<Fragment>
	private lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()
		instance = this
		setupRemoteConfig()
		setupDagger()
	}

	private fun setupRemoteConfig() {
		CSLRemoteConfigManager()
	}

	private fun setupDagger() {
		appComponent = DaggerAppComponent
			.builder()
			.app(this)
			.build()
		appComponent.inject(this)
	}

	private fun setupAircon() {
	}

	override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidFragmentInjector
}