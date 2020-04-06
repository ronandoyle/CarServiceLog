package ie.nanorstudios.carservicelog

import ie.nanorstudios.carservicelog.models.ServiceItem

fun extractItemType(csvString: String): List<ServiceItem> {
	val serviceItems = mutableListOf<ServiceItem>()
	val stringItems = csvString.split(",").map { it.trim() }
	stringItems.forEach {
		serviceItems.add(ServiceItem().apply {
			this.title = it
			this.price = ""
		})
	}
	return serviceItems
}