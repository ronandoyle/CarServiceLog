package ie.nanorstudios.carservicelog.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter


class ItemAdapter @JvmOverloads constructor(context: Context, var resourceId: Int, var items: ArrayList<String>):
	ArrayAdapter<String>(context, resourceId, items) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		var view = convertView
		if (convertView == null) {
			val inflater = (context as Activity).layoutInflater
			view = inflater.inflate(resourceId, parent, false)
		}
		return view!!
	}

	override fun getItem(position: Int): String? = items[position]

	override fun getCount(): Int = items.size


}