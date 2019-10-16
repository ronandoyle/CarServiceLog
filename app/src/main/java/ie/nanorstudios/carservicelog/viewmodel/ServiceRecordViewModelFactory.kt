package ie.nanorstudios.carservicelog.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ServiceRecordViewModelFactory(var application: Application): ViewModelProvider.Factory {

	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return ServiceRecordViewModel(application) as T
	}
}