package ie.nanorstudios.carservicelog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.viewholders.ServiceRecordViewHolder

class ServiceRecordAdapter: RecyclerView.Adapter<ServiceRecordViewHolder>() {
	var serviceRecords: MutableList<ServiceRecord>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRecordViewHolder {
		val v = LayoutInflater.from(parent.context).inflate(R.layout.service_record, parent, false)
		return ServiceRecordViewHolder(v)
	}

	override fun getItemCount(): Int = serviceRecords?.count() ?: 0

	override fun onBindViewHolder(holder: ServiceRecordViewHolder, position: Int) {
		serviceRecords?.let { holder.bind(it[position]) }
	}

	fun setServiceServiceRecords(serviceRecords: MutableList<ServiceRecord>) {
		this.serviceRecords = serviceRecords
		notifyDataSetChanged()
	}

	fun updateServiceRecords(serviceRecord: ServiceRecord) {
		if (serviceRecords == null) {
			serviceRecords = mutableListOf(serviceRecord)
		} else {
			serviceRecords!!.add(serviceRecord)
		}
		notifyDataSetChanged()
	}

}