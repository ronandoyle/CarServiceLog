package ie.nanorstudios.carservicelog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.ServiceType
import kotlinx.android.synthetic.main.record_type_bottom_sheet.*


class RecordTypeBottomSheetFragment(var action: (type: String) -> Unit): BottomSheetDialogFragment() {

	companion object {
		const val TAG = "RecordTypeBottomSheetFragment"
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.record_type_bottom_sheet, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		service_cell?.setOnClickListener { action.invoke(ServiceType.SERVICE.type) }
		fuel_cell?.setOnClickListener { action.invoke(ServiceType.FUEL.type) }
		expense_cell?.setOnClickListener { action.invoke(ServiceType.EXPENSE.type) }
		reminder_cell?.setOnClickListener { action.invoke(ServiceType.REMINDER.type) }
	}
}