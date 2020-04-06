package ie.nanorstudios.carservicelog.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import ie.nanorstudios.carservicelog.CSLRemoteConfigManager
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_NEW_ITEM_TITLE
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_SERVICE_RECORD
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.RequestCodes.Companion.NEW_ITEM_ACTIVITY_REQUEST_CODE
import ie.nanorstudios.carservicelog.ServiceType
import ie.nanorstudios.carservicelog.adapters.ServiceRecordAdapter
import ie.nanorstudios.carservicelog.fragments.RecordTypeBottomSheetFragment
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenter
import ie.nanorstudios.carservicelog.views.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.car_list_toolbar.*
import javax.inject.Inject


class MainActivity: DaggerAppCompatActivity(), MainActivityView {

    @Inject lateinit var presenter: MainActivityPresenter
    private var serviceRecordAdapter = ServiceRecordAdapter()
	private lateinit var drawerToggle: ActionBarDrawerToggle

	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		setupFirebase()
		setupToolbar()
		setupDrawer()
        setupRecyclerView()
//		setupBottomSheet()
        setupExpandingFab()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val serviceRecord = data?.getParcelableExtra<ServiceRecord>(EXTRA_SERVICE_RECORD)
            serviceRecord?.let {
                serviceRecordAdapter.updateServiceRecords(it)
                presenter.insertServiceRecordToDB(it)
            }
        }
    }

	private fun setupToolbar() {
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	private fun setupDrawer() {
		drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)
		drawerToggle.isDrawerIndicatorEnabled = true
		drawerToggle.syncState()
		drawer_layout.addDrawerListener(drawerToggle)

		nav_view?.setNavigationItemSelectedListener {
			when (it.itemId) {
				R.id.add_vehicle -> Toast.makeText(applicationContext, "Show vehicle adding dialog/screen", Toast.LENGTH_SHORT).show()
			}
			drawer_layout.closeDrawers()
			true
		}
	}

	override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onPostCreate(savedInstanceState, persistentState)
		drawerToggle.syncState()
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		drawerToggle.onConfigurationChanged(newConfig)
	}

	private fun setupFirebase() {
		CSLRemoteConfigManager().fetchAndActivate(this)
	}

    private fun setupRecyclerView() {
        val layoutMgr = LinearLayoutManager(this)
        layoutMgr.orientation = RecyclerView.VERTICAL
        service_record_rv.layoutManager = LinearLayoutManager(this)
		service_record_rv.addItemDecoration(DividerItemDecoration(service_record_rv.context, layoutMgr.orientation))
        service_record_rv.adapter = serviceRecordAdapter
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        presenter.populateRecyclerView()
    }

    override fun populateServiceRecords(serviceRecords: MutableList<ServiceRecord>) {
        serviceRecordAdapter.setServiceServiceRecords(serviceRecords)
    }

	override fun unableToPopulateServiceRecords() {
		Toast.makeText(this, "Unable to fetch service records", Toast.LENGTH_SHORT).show()
	}

	private fun setupExpandingFab() {
        fab.setOnClickListener {
			showBottomSheetFragment()
        }
    }

	private fun showBottomSheetFragment() {
		val bottomSheetFragment = RecordTypeBottomSheetFragment(this::handleBottomSheetAction)
		bottomSheetFragment.show(supportFragmentManager, RecordTypeBottomSheetFragment.TAG)
	}

	private fun handleBottomSheetAction(serviceType: String) {

		if (serviceType == ServiceType.EXPENSE.type) {
			presenter.populateRecyclerView()
			return
		}

		val intent = Intent(this, NewItemActivity::class.java).apply {
			putExtra(EXTRA_NEW_ITEM_TITLE, serviceType)
		}

		val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_up, R.anim.slide_down)
		startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE, options.toBundle())

		(supportFragmentManager.findFragmentByTag(RecordTypeBottomSheetFragment.TAG) as RecordTypeBottomSheetFragment).dismiss()
	}
}
