package ie.nanorstudios.carservicelog.presenters

interface MainActivityPresenter {
	fun handleResume()
	fun handlePause()
	fun insertCarToDB()
}