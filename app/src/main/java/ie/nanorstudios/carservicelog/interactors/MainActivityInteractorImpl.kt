package ie.nanorstudios.carservicelog.interactors

import ie.nanorstudios.carservicelog.ServiceRecordRepository
import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainActivityInteractorImpl @Inject constructor(var repo: ServiceRecordRepository): MainActivityInteractor {

	override fun insertServiceRecord(serviceRecord: ServiceRecord): Completable = repo.insertServiceRecord(serviceRecord)

	override fun fetchServiceRecords(): Single<MutableList<ServiceRecord>> = repo.getServiceRecords()
}