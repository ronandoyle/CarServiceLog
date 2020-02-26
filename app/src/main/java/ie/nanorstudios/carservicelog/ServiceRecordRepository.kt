package ie.nanorstudios.carservicelog

import android.app.Application
import ie.nanorstudios.carservicelog.database.ServiceRecordDatabase
import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ServiceRecordRepository @Inject constructor(application: Application) {
	private var serviceRecordDao = ServiceRecordDatabase.getDatabase(application).serviceRecordDao()

	fun insertServiceRecord(serviceRecord: ServiceRecord): Completable = serviceRecordDao.insert(serviceRecord)

	fun getServiceRecords(): Single<MutableList<ServiceRecord>> = serviceRecordDao.fetchServiceRecords()
}