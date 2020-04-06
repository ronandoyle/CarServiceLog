package ie.nanorstudios.carservicelog.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.nanorstudios.carservicelog.CSLRemoteConfigManager
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_NEW_ITEM_TITLE
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_SERVICE_RECORD
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.ServiceType
import ie.nanorstudios.carservicelog.ServiceType.*
import ie.nanorstudios.carservicelog.adapters.ItemAdapter
import ie.nanorstudios.carservicelog.fragments.ServiceItemsFragment
import ie.nanorstudios.carservicelog.models.ServiceItem
import ie.nanorstudios.carservicelog.models.ServiceRecord
import kotlinx.android.synthetic.main.activity_new_fuel_item.*
import kotlinx.android.synthetic.main.activity_new_service_item.dateView
import kotlinx.android.synthetic.main.activity_new_service_item.mileageInput
import kotlinx.android.synthetic.main.activity_new_service_item.serviceItem
import kotlinx.android.synthetic.main.activity_new_service_item.submit
import kotlinx.android.synthetic.main.activity_new_service_item.toolbar
import java.text.SimpleDateFormat
import java.util.*

class NewItemActivity: AppCompatActivity() {

	private var serviceType: ServiceType? = null
	private var serviceItemAdapter: ItemAdapter? = null
	private var shouldShowMenuTick = false
	private var selectedServiceItems: MutableList<ServiceItem>? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		extractIntentData()
		val layoutId = getLayoutID()
		setContentView(layoutId)
		initUI()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == android.R.id.home) {
			onBackPressed()
			return false
		} else if (item.itemId == R.id.action_done) {
			confirmListSelection()
			return false
		}
		return super.onOptionsItemSelected(item)
	}

	fun confirmListSelection() {
		selectedServiceItems =
			(supportFragmentManager.findFragmentByTag(ServiceItemsFragment.TAG) as ServiceItemsFragment).getSelectedItems()
		populateServiceTypeView(selectedServiceItems)
		supportFragmentManager.beginTransaction()
			.remove(supportFragmentManager.findFragmentByTag(ServiceItemsFragment.TAG)!!).commit()
		shouldShowMenuTick = false
		invalidateOptionsMenu()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.tick_menu, menu)
		menu?.findItem(R.id.action_done)?.isVisible = shouldShowMenuTick
		return super.onCreateOptionsMenu(menu)
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.slide_down, R.anim.slide_up_reverse)
	}

	override fun onBackPressed() {
		if (supportFragmentManager.findFragmentByTag(ServiceItemsFragment.TAG) != null && supportFragmentManager.findFragmentByTag(ServiceItemsFragment.TAG)!!.isAdded) {
			supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentByTag(ServiceItemsFragment.TAG)!!).commit()
			shouldShowMenuTick = false
			invalidateOptionsMenu()
		} else {
			super.onBackPressed()
		}
	}

	private fun extractIntentData() {
		values().forEach {
			if (it.type == intent.getStringExtra(EXTRA_NEW_ITEM_TITLE)) {
				serviceType = it
			}
		}
		if (serviceType == null) {
			serviceType = SERVICE
		}
	}

	private fun getLayoutID(): Int {
		return when(serviceType) {
			SERVICE -> R.layout.activity_new_service_item
			FUEL -> R.layout.activity_new_fuel_item
			EXPENSE -> R.layout.activity_new_expense_item
			REMINDER -> R.layout.activity_new_reminder_item
			else -> R.layout.activity_new_service_item
		}
	}

	private fun initUI() {
		initToolbar()
		initDate()
		initSubmitBtn()
//		initAutocompleteList()
		applyServiceItemClickListener()
		applyFuelCalculationListeners()
	}

	private fun initToolbar() {
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = serviceType?.type
	}

	private fun initDate() {
		applyCurrentDate()
		dateView.setOnClickListener { showDatePicker() }
	}

	private fun applyCurrentDate() {
		dateView.text = SimpleDateFormat.getDateInstance().format(Calendar.getInstance().time)
	}

	private fun showDatePicker() {
		val c = Calendar.getInstance()
		val year = c.get(Calendar.YEAR)
		val month = c.get(Calendar.MONTH)
		val day = c.get(Calendar.DAY_OF_MONTH)
		DatePickerDialog(this, datePickerListener, year, month, day).show()
	}

	private val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
		val calendar = Calendar.getInstance()
		calendar.set(year, month, day)
		dateView.text = SimpleDateFormat.getDateInstance().format(calendar.time)
	}

	private fun initSubmitBtn() {
		submit.setOnClickListener {
			val serviceRecord = ServiceRecord().apply {
				this.type = serviceType?.type
				this.date = dateView.text.toString()
				this.serviceItems = selectedServiceItems as MutableList<ServiceItem>?
				this.mileage = mileageInput.text.toString()

				if (type == FUEL.type) {
					serviceItems?.get(0)?.price = fuelTotalCost?.editText?.text?.toString()
				}
			}
			val data = Intent().apply {
				putExtra(EXTRA_SERVICE_RECORD, serviceRecord)
			}

			setResult(Activity.RESULT_OK, data)
			finish()
		}
	}

	private fun populateServiceTypeView(items: MutableList<ServiceItem>?) {
		items?.forEach {
			if (it.price.isNullOrEmpty()) {
				serviceItem.text = serviceItem.text.toString().plus("\n${it.title}")
			} else {
				serviceItem.text = serviceItem.text.toString().plus("\n${it.title} - ${it.price}")
			}
		}
	}

	private fun applyServiceItemClickListener() {
		serviceItem.setOnClickListener {
			val fragment = ServiceItemsFragment.newInstance(
				serviceType?.type,
				CSLRemoteConfigManager().getServiceItems(serviceType),
				selectedServiceItems,
				serviceType == FUEL
			)

			var ft = supportFragmentManager.beginTransaction()
			ft.add(R.id.frame_layout, fragment, ServiceItemsFragment.TAG)
			ft.commit()
			shouldShowMenuTick = true
			invalidateOptionsMenu()
		}
	}

	private fun applyFuelCalculationListeners() {
//		fuelTotalCost?.editText?.addTextChangedListener(object: TextWatcher {
//			override fun afterTextChanged(p0: Editable?) {
//				if (!fuelTotalLitres?.editText?.toString().isNullOrEmpty()) {
//					fuelPricePerLitre?.editText?.setText((fuelTotalCost?.editText?.text?.toString()?.toDouble()!! / fuelTotalLitres.editText.toString().toDouble()).toString())
//				}
//			}
//
//			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//			}
//		})
	}





//	private fun initAutocompleteList() {
//		val items = CSLRemoteConfigManager().getServiceItems(serviceType)
////		serviceItemAdapter = ItemAdapter(typeView.context, android.R.layout.select_dialog_item, items)
//
//		val builder = AlertDialog.Builder(serviceItem.context)
//		builder.setTitle("Choose a service type")
//
//// add a checkbox list
////		builder.setMultiChoiceItems(items.toTypedArray(), null) { dialog, which, isChecked ->
//			// user checked or unchecked a box
////		}
//
//// add OK and Cancel buttons
//		builder.setPositiveButton("OK") { dialog, which ->
//			// user clicked OK
//		}
//		builder.setNegativeButton("Cancel", null)
//
//// create and show the alert dialog
//		val dialog = builder.create()
//		dialog.show()
//	}
}