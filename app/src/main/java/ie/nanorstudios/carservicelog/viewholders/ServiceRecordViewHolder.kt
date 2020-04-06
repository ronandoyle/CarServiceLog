package ie.nanorstudios.carservicelog.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.ServiceType
import ie.nanorstudios.carservicelog.models.ServiceItem
import ie.nanorstudios.carservicelog.models.ServiceRecord

class ServiceRecordViewHolder(var item: View): RecyclerView.ViewHolder(item) {
	val icon: ImageView? = item.findViewById(R.id.service_record_iv)
	val title: TextView? = item.findViewById(R.id.title_tv)
	val date: TextView? = item.findViewById(R.id.date_tv)
	val price: TextView? = item.findViewById(R.id.price_tv)
	val mileage: TextView? = item.findViewById(R.id.mileage_tv)

	var isTitleExpanded: Boolean = false

	lateinit var serviceRecord: ServiceRecord

	fun bind(record: ServiceRecord) {
		serviceRecord = record
		serviceRecord.type?.let {
			val iconResId = when(it) {
				ServiceType.SERVICE.type -> R.drawable.ic_service_grey_24dp
				ServiceType.FUEL.type -> R.drawable.ic_fuel_grey_24dp
				ServiceType.EXPENSE.type -> R.drawable.ic_expense_grey_24dp
				ServiceType.REMINDER.type -> R.drawable.ic_reminder_grey_24dp
				else -> R.drawable.ic_service_grey_24dp
			}
			icon?.setImageDrawable(ContextCompat.getDrawable(icon.context, iconResId))
		}

		populateCollapsedTitle()

		item.setOnClickListener {
			if (isTitleExpanded) {
				populateCollapsedTitle()
			} else {
				populateExpandedTitle()
			}
		}
		date?.text = serviceRecord.date
		populatePrice()
		populateMileage()
	}

	private fun populateCollapsedTitle() {
		var titleText: String = if (serviceRecord.serviceItems?.isNotEmpty() == true) {
			if (serviceRecord.serviceItems!!.size > 1) {
				serviceRecord.serviceItems!![0].title.plus(" (+${serviceRecord.serviceItems!!.size - 1})")
			} else {
				serviceRecord.serviceItems!![0].title ?: ""
			}
		} else {
			serviceRecord.type ?: ""
		}

		title?.text = titleText
		isTitleExpanded = false
	}

	private fun populateExpandedTitle() {
		var titleText = ""
		serviceRecord.serviceItems?.let { records ->
			if (records.size > 1) {
				records.forEach {
					titleText = if (records.indexOf(it) == records.size -1) {
						titleText.plus(addPriceIfAvailable(it))
					} else {
						titleText.plus(addPriceIfAvailable(it) + "\n")
					}
				}
				title?.text = titleText
				isTitleExpanded = true
			}
		}
	}

	private fun addPriceIfAvailable(item: ServiceItem): String? = if (!item.price.isNullOrEmpty()) {
		"${item.title} - " + String.format(title?.resources?.getString(R.string.euro_symbol)!!, "${item.price}")
	} else {
		item.title
	}

	private fun populatePrice() {
		var total: Double = 0.0
		serviceRecord.serviceItems?.forEach {
			if (!it.price.isNullOrEmpty()) {
				total += it.price!!.toDouble()
			}
		}

		if (total == 0.0) {
			price?.text = ""
		} else {
			price?.text = String.format(price?.resources?.getString(R.string.euro_symbol)!!, "$total")
		}
	}

	private fun populateMileage() {
		if (serviceRecord.mileage.isNullOrEmpty()) {
			mileage?.visibility = View.GONE
		} else {
			mileage?.text = serviceRecord.mileage
		}
	}
}