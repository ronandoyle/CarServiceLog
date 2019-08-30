package ie.nanorstudios.carservicelog.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import ie.nanorstudios.carservicelog.activities.MainActivity
import ie.nanorstudios.carservicelog.interactors.MainActivityInteractor
import ie.nanorstudios.carservicelog.interactors.MainActivityInteractorImpl
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenter
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenterImpl
import ie.nanorstudios.carservicelog.views.MainActivityView

@Module
abstract class MainActivityModule {

	@Binds
	@ActivityScope
	abstract fun bindActivity(activity: MainActivity): Activity

	@Binds
	@ActivityScope
	abstract fun bindMainActivityPresenter(presenter: MainActivityPresenterImpl): MainActivityPresenter

	@Binds
	@ActivityScope
	abstract fun bindMainActivityInteractor(interactor: MainActivityInteractorImpl): MainActivityInteractor

	@Binds
	@ActivityScope
	abstract fun bindMainActivityView(view: MainActivity): MainActivityView
}