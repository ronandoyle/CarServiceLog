package ie.nanorstudios.carservicelog.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.models.ServiceRecord

class ServiceRecordViewHolder(var item: View): RecyclerView.ViewHolder(item) {

	fun bind(record: ServiceRecord) {
		item.findViewById<TextView>(R.id.title_tv).text = record.title
	}
}