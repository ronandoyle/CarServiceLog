package ie.nanorstudios.carservicelog.presenters

import ie.nanorstudios.carservicelog.models.ServiceRecord

interface MainActivityPresenter {
	fun handleResume()
	fun handlePause()
	fun insertCarToDB()
	fun insertServiceRecordToDB(serviceRecord: ServiceRecord)
	fun populateRecyclerView()
}