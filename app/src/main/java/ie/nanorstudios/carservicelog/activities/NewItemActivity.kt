package ie.nanorstudios.carservicelog.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.nanorstudios.carservicelog.R
import kotlinx.android.synthetic.main.activity_new_item.*

class NewItemActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_item)
		setupToolbar()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == android.R.id.home) {
			onBackPressed()
			return false
		}
		return super.onOptionsItemSelected(item)
	}

	private fun setupToolbar() {
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}