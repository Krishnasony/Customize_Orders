package i.krishnasony.customizeorders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.repo.CustomizeRepo
import i.krishnasony.customizeorders.room.database.AppDataBase
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.utils.observeOnce
import i.krishnasony.customizeorders.viewModel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CustomPizzaListDialog : DialogFragment(),RemoveClickListener {

    private lateinit var rootView: View
    private lateinit var adapter: CustomPizzaAdapter
    private var customPizzaList:ArrayList<CustomPizza> = arrayListOf()
    private val database: AppDataBase by inject()
    private val orderViewModel: OrderViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.layout_pizza_list, container,false)
        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.customPizzaRecyclerView)
        adapter = CustomPizzaAdapter(this,context!!,customPizzaList)
        recyclerView.layoutManager = LinearLayoutManager(context!!,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
        getCustomPizzaList()
    }

    private fun getCustomPizzaList() {
        val repo = CustomizeRepo(database.customPizzaDao)
        GlobalScope.launch(Dispatchers.Main) {
            orderViewModel.getCustomPizza(repo)
            orderViewModel.customPizzaList.observeOnce(this@CustomPizzaListDialog, Observer {
                list->
                list?.let {
                    customPizzaList = list as ArrayList<CustomPizza>
                    adapter.customPizzaList = customPizzaList
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onItemRemoved(customPizza: CustomPizza,pos:Int) {
        customPizzaList.removeAt(pos)
        adapter.notifyDataSetChanged()
    }


    companion object {
        fun newInstance():CustomPizzaListDialog{
            return CustomPizzaListDialog()
        }
    }
}