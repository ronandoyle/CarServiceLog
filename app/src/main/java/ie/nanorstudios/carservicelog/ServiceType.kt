package ie.nanorstudios.carservicelog

enum class ServiceType(var type: String) {
	SERVICE("Service"),
	FUEL("Fuel"),
	EXPENSE("Expense"),
	REMINDER("Reminder")
}