package ie.nanorstudios.carservicelog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.nanorstudios.carservicelog.Extras
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_COST_LIST
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_IS_SINGLE_SELECT
import ie.nanorstudios.carservicelog.Extras.Companion.EXTRA_SELECTED_COST_LIST
import ie.nanorstudios.carservicelog.R
import ie.nanorstudios.carservicelog.activities.NewItemActivity
import ie.nanorstudios.carservicelog.adapters.ServiceItemAdapter
import ie.nanorstudios.carservicelog.models.ServiceItem
import kotlinx.android.synthetic.main.fragment_cost_list.*
import java.util.*

class ServiceItemsFragment: Fragment() {

	private var serviceItems: List<ServiceItem>? = null
	private var selectedServiceItems: List<ServiceItem>? = null
	private var title: String? = null
	private var adapter: ServiceItemAdapter? = null
	private var isSingleSelectList: Boolean = false

	companion object {
		const val TAG = "ServiceItemsFragment"
		fun newInstance(title: String?, serviceItems: List<ServiceItem>?, selectedServiceItems: List<ServiceItem>?, isSingleSelectList: Boolean) = ServiceItemsFragment().apply {
			arguments = Bundle(1).apply {
				putString(Extras.EXTRA_NEW_ITEM_TITLE, title)
				putParcelableArrayList(EXTRA_COST_LIST, serviceItems as ArrayList<ServiceItem>?)
				putParcelableArrayList(EXTRA_SELECTED_COST_LIST, selectedServiceItems as ArrayList<ServiceItem>?)
				putBoolean(EXTRA_IS_SINGLE_SELECT, isSingleSelectList)
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		extractArguments()
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.tick_menu, menu)
	}

	private fun extractArguments() {
		serviceItems = arguments?.getParcelableArrayList(EXTRA_COST_LIST)
		selectedServiceItems = arguments?.getParcelableArrayList(EXTRA_SELECTED_COST_LIST)
		isSingleSelectList = arguments?.getBoolean(EXTRA_IS_SINGLE_SELECT, false) ?: false
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.fragment_cost_list, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		recycler_view.layoutManager = LinearLayoutManager(recycler_view.context)
		adapter = ServiceItemAdapter(serviceItems, selectedServiceItems as MutableList<ServiceItem>?, isSingleSelectList, ::onSingleSelectItemSelected)
		recycler_view.adapter = adapter
	}

	fun getSelectedItems(): MutableList<ServiceItem>? = adapter?.selectedServiceItems

	private fun onSingleSelectItemSelected() {
		(activity as NewItemActivity).confirmListSelection()
	}
}