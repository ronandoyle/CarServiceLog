package ie.nanorstudios.carservicelog

import android.app.Application
import androidx.lifecycle.LiveData
import ie.nanorstudios.carservicelog.database.ServiceRecordDatabase
import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Single

class ServiceRecordRepository(application: Application) {
	private var serviceRecordDao = ServiceRecordDatabase.getDatabase(application).serviceRecordDao()

	private var serviceRecords: LiveData<List<ServiceRecord>> = serviceRecordDao.fetchServiceRecords()

	fun insertServiceRecord(serviceRecord: ServiceRecord): Single<Any> {
		serviceRecord.id = serviceRecords.value?.size?.toLong() ?: 0

		return Single.create<Any> {
			serviceRecordDao.insert(serviceRecord)
		}
	}

	fun getServiceRecords(): LiveData<List<ServiceRecord>> = serviceRecords
}