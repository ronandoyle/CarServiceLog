package ie.nanorstudios.carservicelog.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.nanorstudios.carservicelog.models.ServiceItem
import java.util.*


class ServiceItemTypeConverter {

	@TypeConverter
	fun serviceItemsToString(serviceItems: MutableList<ServiceItem>?): String? {
		val json = Gson().toJson(serviceItems)
		return json
	}

	@TypeConverter
	fun stringToServiceItems(serviceItemString: String?): MutableList <ServiceItem>? {
		val gson = Gson()

		serviceItemString?.let {
			val listType = object : TypeToken<MutableList<ServiceItem>?>() {}.type
			return gson.fromJson(it, listType)
		}
		return Collections.emptyList()
	}
}