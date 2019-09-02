package ie.nanorstudios.carservicelog.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.adapters.ServiceRecordAdapter
import ie.nanorstudios.carservicelog.models.ServiceRecord
import ie.nanorstudios.carservicelog.presenters.MainActivityPresenter
import ie.nanorstudios.carservicelog.views.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity: DaggerAppCompatActivity(), MainActivityView {

    @Inject lateinit var presenter: MainActivityPresenter
    private var serviceRecordAdapter: ServiceRecordAdapter? = null

    private var tempList = mutableListOf<ServiceRecord>()
    private var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupExpandingFab()
    }

    private fun setupRecyclerView() {
        for (i in 0..100) {
            tempList.add(ServiceRecord("$i"))
        }

        serviceRecordAdapter = ServiceRecordAdapter(tempList)
        service_record_rv.layoutManager = LinearLayoutManager(this)
        service_record_rv.adapter = serviceRecordAdapter
    }

    private fun setupExpandingFab() {
        fab.setOnClickListener {
            if(isFABOpen) {
                closeFABMenu()
            } else {
                showFABMenu()
            }
        }

        service_fab.setOnClickListener {
            val intent = Intent(this, NewItemActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        cleaning_fab.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fuel_fab.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        service_fab.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        fab.animate().rotation(50f)
    }

    private fun closeFABMenu() {
        isFABOpen = false
        cleaning_fab.animate().translationY(0f)
        fuel_fab.animate().translationY(0f)
        service_fab.animate().translationY(0f)
        fab.animate().rotation(0f)
    }
}
