package ie.nanorstudios.carservicelog.presenters

import ie.nanorstudios.carservicelog.views.MainActivityView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor(var view: MainActivityView): MainActivityPresenter {

	private var disposables = CompositeDisposable()

	override fun handleResume() {}

	override fun handlePause() {}

	override fun insertCarToDB() {}


}