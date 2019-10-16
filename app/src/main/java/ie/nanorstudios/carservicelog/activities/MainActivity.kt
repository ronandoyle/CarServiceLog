package ie.nanorstudios.carservicelog.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_SERVICE_RECORD
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.RequestCodes.Companion.NEW_ITEM_ACTIVITY_REQUST_CODE
import ie.nanorstudios.carservicelog.adapters.ServiceRecordAdapter
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenter
import ie.nanorstudios.carservicelog.viewmodel.ServiceRecordViewModel
import ie.nanorstudios.carservicelog.viewmodel.ServiceRecordViewModelFactory
import ie.nanorstudios.carservicelog.views.MainActivityView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity: DaggerAppCompatActivity(), MainActivityView {

    @Inject lateinit var presenter: MainActivityPresenter
    private lateinit var serviceRecordViewModel: ServiceRecordViewModel
    private var serviceRecordAdapter = ServiceRecordAdapter()

    private var tempList = mutableListOf<ServiceRecord>()
    private var isFABOpen = false

    private var disposables = CompositeDisposable()


    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupExpandingFab()

        serviceRecordViewModel = ViewModelProviders.of(this@MainActivity, ServiceRecordViewModelFactory(application)).get(ServiceRecordViewModel::class.java)
        serviceRecordViewModel.getAllServiceRecords().observe(this,
            Observer<List<ServiceRecord>> { records -> serviceRecordAdapter.setServiceServiceRecords(records)})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_ITEM_ACTIVITY_REQUST_CODE && resultCode == Activity.RESULT_OK) {
            val serviceRecord = data?.getParcelableExtra<ServiceRecord>(EXTRA_SERVICE_RECORD)
            serviceRecord?.let {

//                disposables.add(serviceRecordViewModel.insert(it)
//                    .subscribeOn())

                // Move the serviceRecordViewModel to the presenter and along with the insertion etc.
            }
        }
    }

    private fun setupRecyclerView() {
        service_record_rv.layoutManager = LinearLayoutManager(this)
        service_record_rv.adapter = serviceRecordAdapter
    }

    private fun setupExpandingFab() {
        fab.setOnClickListener {
//            if(isFABOpen) {
//                closeFABMenu()
//            } else {
//                showFABMenu()
//            }

            val intent = Intent(this, NewItemActivity::class.java)

            startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUST_CODE)
        }

//        service_fab.setOnClickListener {
//            val intent = Intent(this, NewItemActivity::class.java)
//            startActivity(intent)
//        }
    }

//    private fun showFABMenu() {
//        isFABOpen = true
//        cleaning_fab.animate().translationY(-resources.getDimension(R.dimen.standard_55))
//        fuel_fab.animate().translationY(-resources.getDimension(R.dimen.standard_105))
//        service_fab.animate().translationY(-resources.getDimension(R.dimen.standard_155))
//        fab.animate().rotation(50f)
//    }
//
//    private fun closeFABMenu() {
//        isFABOpen = false
//        cleaning_fab.animate().translationY(0f)
//        fuel_fab.animate().translationY(0f)
//        service_fab.animate().translationY(0f)
//        fab.animate().rotation(0f)
//    }
}
