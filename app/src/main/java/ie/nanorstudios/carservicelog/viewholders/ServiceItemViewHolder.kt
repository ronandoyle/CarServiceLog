package ie.nanorstudios.carservicelog.viewholders

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.models.ServiceItem

class ServiceItemViewHolder(var item: View, var addItem: (serviceItem: ServiceItem) -> Unit): RecyclerView.ViewHolder(item) {

	private val checkbox: CheckBox? = item.findViewById(R.id.checkbox)
	private val title: TextView? = item.findViewById(R.id.title_tv)
	private val price: TextInputLayout? = item.findViewById(R.id.price_container)

	fun bind(serviceItem: ServiceItem, isChecked: Boolean) {
		checkbox?.isChecked = isChecked

		title?.text = serviceItem.title
					checkbox?.setOnCheckedChangeListener { compoundButton, isChecked ->
			if (isChecked) {
				title?.let { addItem.invoke(serviceItem) }
				price?.visibility = View.VISIBLE
		} else {
				price?.visibility = View.GONE
			}
		}

		title?.setOnClickListener {
			checkbox?.let {
				it.isChecked = !checkbox.isChecked
			}
		}

		price?.editText?.addTextChangedListener(object : TextWatcher {

			override fun afterTextChanged(s: Editable) {
				serviceItem.price = s.toString()
			}

			override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
		});
	}
}