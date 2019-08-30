package ie.nanorstudios.carservicelog.activities

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        for (i in 0..100) {
            tempList.add(ServiceRecord("$i"))
        }

        serviceRecordAdapter = ServiceRecordAdapter(tempList)
        service_record_rv.layoutManager = LinearLayoutManager(this)
        service_record_rv.adapter = serviceRecordAdapter
    }


}
