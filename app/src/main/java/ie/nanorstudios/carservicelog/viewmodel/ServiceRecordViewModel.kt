package ie.nanorstudios.carservicelog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ie.nanorstudios.carservicelog.ServiceRecordRepository
import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Maybe

open class ServiceRecordViewModel(open var app: Application): AndroidViewModel(app) {
	private var repository = ServiceRecordRepository(app)
	private var serviceRecords: LiveData<List<ServiceRecord>> = repository.getServiceRecords()

	fun insert(serviceRecord: ServiceRecord): Maybe<Any> {
		return repository.insertServiceRecord(serviceRecord)
	}

	fun getAllServiceRecords(): LiveData<List<ServiceRecord>> = serviceRecords

//	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//	fun onResume() {
//		increment()
//	}

//
//	fun getServiceRecordsList(): MutableLiveData<List<ServiceRecord>> {
//		if (serviceRecordsList == null) {
//			serviceRecordsList = MutableLiveData()
//			loadServiceRecords()
//		}
//		return serviceRecordsList as MutableLiveData<List<ServiceRecord>>
//	}
//
//	private fun loadServiceRecords() {
//
//	}
}