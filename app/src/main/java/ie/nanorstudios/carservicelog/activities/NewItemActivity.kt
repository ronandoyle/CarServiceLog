package ie.nanorstudios.carservicelog.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.nanorstudios.carservicelog.R
import kotlinx.android.synthetic.main.activity_new_item.*
import java.text.SimpleDateFormat
import java.util.*

class NewItemActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_item)
		initUI()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == android.R.id.home) {
			onBackPressed()
			return false
		}
		return super.onOptionsItemSelected(item)
	}

	private fun initUI() {
		initToolbar()
		initDate()
	}

	private fun initToolbar() {
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
}