package ie.nanorstudios.carservicelog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.models.ServiceItem
import ie.nanorstudios.carservicelog.viewholders.ServiceItemViewHolder

class ServiceItemAdapter(var serviceItems: List<ServiceItem>?,
						 var selectedServiceItems: MutableList<ServiceItem>?,
						 var isSingleSelectList: Boolean,
						 var onSingleSelectItemSelected: () -> Unit): RecyclerView.Adapter<ServiceItemViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceItemViewHolder {
		val v = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
		return ServiceItemViewHolder(v, ::addSelectedItem)
	}

	override fun getItemCount(): Int = serviceItems?.count() ?: 0

	override fun onBindViewHolder(holder: ServiceItemViewHolder, position: Int) {
		var isSelected = false
		serviceItems?.let {
			var serviceItem = it[position]
			selectedServiceItems?.forEach { item ->
				if (serviceItem.title == item.title) {
					isSelected = true
				}
			}
			holder.bind(it[position], isSelected)
		}
	}

	fun addSelectedItem(item: ServiceItem) {
		if (selectedServiceItems == null) {
			selectedServiceItems = mutableListOf()
		}
		selectedServiceItems?.let {
			if (!it.contains(item)) {
				it.add(item)
				if (isSingleSelectList) {
					onSingleSelectItemSelected()
				}
			}
		}
	}

}