package ie.nanorstudios.carservicelog.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.DaggerAppCompatActivity
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_NEW_ITEM_TITLE
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_SERVICE_RECORD
import ie.nanorstudios.carservicelog.RequestCodes.Companion.NEW_ITEM_ACTIVITY_REQUEST_CODE
import ie.nanorstudios.carservicelog.adapters.ServiceRecordAdapter
import ie.nanorstudios.carservicelog.fragments.RecordTypeBottomSheetFragment
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenter
import ie.nanorstudios.carservicelog.views.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity: DaggerAppCompatActivity(), MainActivityView {

    @Inject lateinit var presenter: MainActivityPresenter
    private var serviceRecordAdapter = ServiceRecordAdapter()
	private var sheetBehavior: BottomSheetBehavior<*>? = null

	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ie.nanorstudios.carservicelog.R.layout.activity_main)
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

    private fun setupRecyclerView() {
        val layoutMgr = LinearLayoutManager(this)
        layoutMgr.orientation = RecyclerView.VERTICAL
        service_record_rv.layoutManager = LinearLayoutManager(this)
        service_record_rv.adapter = serviceRecordAdapter
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        presenter.populateRecyclerView()
    }

    override fun populateServiceRecords(serviceRecords: MutableList<ServiceRecord>) {
        serviceRecordAdapter.setServiceServiceRecords(serviceRecords)
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
		val intent = Intent(this, NewItemActivity::class.java).apply {
			putExtra(EXTRA_NEW_ITEM_TITLE, serviceType)
		}
		startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE)
		(supportFragmentManager.findFragmentByTag(RecordTypeBottomSheetFragment.TAG) as RecordTypeBottomSheetFragment).dismiss()
	}
}
