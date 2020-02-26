package ie.nanorstudios.carservicelog.views

import ie.nanorstudios.carservicelog.models.ServiceRecord

interface MainActivityView {
	fun populateServiceRecords(serviceRecords: MutableList<ServiceRecord>)
}