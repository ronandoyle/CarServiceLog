package ie.nanorstudios.carservicelog.interactors

import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Completable
import io.reactivex.Single

interface MainActivityInteractor {
	fun insertServiceRecord(serviceRecord: ServiceRecord): Completable
	fun fetchServiceRecords(): Single<MutableList<ServiceRecord>>
}