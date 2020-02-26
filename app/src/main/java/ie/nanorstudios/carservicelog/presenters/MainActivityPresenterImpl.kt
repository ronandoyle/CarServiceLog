package ie.nanorstudios.carservicelog.presenters

import ie.nanorstudios.carservicelog.di.RxModule
import ie.nanorstudios.carservicelog.interactors.MainActivityInteractor
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.views.MainActivityView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class MainActivityPresenterImpl @Inject constructor(var view: MainActivityView,
													var interactor: MainActivityInteractor,
													@Named(RxModule.IO_THREAD) var ioThread: Scheduler,
													@Named(RxModule.MAIN_THREAD) var mainThread: Scheduler): MainActivityPresenter {

	private var disposables = CompositeDisposable()

	override fun handleResume() {}
	override fun handlePause() {}
	override fun insertCarToDB() {}

	override fun insertServiceRecordToDB(serviceRecord: ServiceRecord) {
		disposables.add(interactor.insertServiceRecord(serviceRecord)
			.subscribeOn(ioThread)
			.observeOn(mainThread)
			.subscribe())
	}

	override fun populateRecyclerView() {
		disposables.add(interactor.fetchServiceRecords()
			.subscribeOn(ioThread)
			.observeOn(mainThread)
			.subscribe(this::fetchServiceRecordsSuccess, this::fetchServiceRecordsError))
	}

	private fun fetchServiceRecordsSuccess(serviceRecords: MutableList<ServiceRecord>) {
		if (serviceRecords.isNotEmpty()) {
			view.populateServiceRecords(serviceRecords)
		}

	}

	private fun fetchServiceRecordsError(e: Throwable) {
		//TODO Handle error
	}
}