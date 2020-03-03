package ie.nanorstudios.carservicelog

import android.app.Activity
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class CSLRemoteConfigManager {
	var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
	private val cacheTimeout: Long = if (BuildConfig.DEBUG) 0 else 86400

	companion object {
		const val SERVICE_TYPES = "service_types"
		const val EXPENSE_TYPES = "expense_types"
		const val FUEL_TYPES = "fuel_types"
	}

	init {
		val configSettings = FirebaseRemoteConfigSettings.Builder()
			.setFetchTimeoutInSeconds(cacheTimeout)
			.build()
		remoteConfig.setConfigSettingsAsync(configSettings)
		remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
	}

	fun fetchAndActivate(activity: Activity) {
		remoteConfig.fetchAndActivate().addOnCompleteListener(activity) {
			if (it.isSuccessful) {
				Log.d("CSL_", "Remote config params updated ${it.result}")
			} else {
				Log.d("CSL_", "Remote config failed ${it.result}")
			}
		}
	}
}