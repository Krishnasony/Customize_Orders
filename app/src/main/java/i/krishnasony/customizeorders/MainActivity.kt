package i.krishnasony.customizeorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import i.krishnasony.customizeorders.databinding.ActivityMainBinding
import i.krishnasony.customizeorders.network.ApiService
import i.krishnasony.customizeorders.repo.CustomizeRepo
import i.krishnasony.customizeorders.repo.OrderRepo
import i.krishnasony.customizeorders.room.database.AppDataBase
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.Size
import i.krishnasony.customizeorders.ui.CustomizePizzaDialog
import i.krishnasony.customizeorders.utils.observeOnce
import i.krishnasony.customizeorders.utils.progressDialog
import i.krishnasony.customizeorders.utils.showToast
import i.krishnasony.customizeorders.viewModel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    /** koin injection for database, retrofit apiService and viewModel */
    private val database:AppDataBase by inject()
    private val apiService:ApiService by inject()
    private val orderViewModel:OrderViewModel by viewModel()
    private lateinit var progressBar:AlertDialog
    private var visibility = true
    private var checkDataBaseData = false
    private var crustLists:ArrayList<Crust> = arrayListOf()
    private var sizeList:ArrayList<Size> = arrayListOf()
    private var defaultCrust= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        with(dataBinding){
            addClickListener = onAddClickListener
            removeClickListener = onRemoveClickListener
            isVisible = visibility
        }
        progressBar = this.progressDialog("Loading...")
        setToolbar()
        checkDataBaseEntry()
        getDataFromApi()
    }

    private fun checkDataBaseEntry() {
        checkDataBaseData = database.customPizzaDao.getCrustId()!=null
    }

    private fun getDataFromApi() {
        visibility = false
        dataBinding.isVisible = visibility
        progressBar.show()
        val repo = OrderRepo(apiService)
        GlobalScope.launch(Dispatchers.Main) {
            orderViewModel.getOrders(repo)
            orderViewModel.orderLiveData.observeOnce(this@MainActivity, Observer {
                order->
                order?.let {
                    dataBinding.order = it
                    defaultCrust = order.defaultCrust.toString()
                    it.crusts?.forEach {crust->
                        crust?.let {
                            crustLists.add(Crust(id = crust.id.toString(),name = crust.name!!,defaultSize = crust.defaultSize!!))
                            crust.sizes?.forEach {size->
                                size?.let {
                                    sizeList.add(Size(id = size.id.toString(),name = size.name.toString(),price = size.price!!,crustId = crust.id.toString() ))
                                }
                            }
                        }
                    }
                    if (!checkDataBaseData){
                        insertCrustAndSizeList(crustLists,sizeList)
                    }
                    visibility = true
                    dataBinding.isVisible = visibility
                    progressBar.dismiss()
                    this@MainActivity.showToast(it.name.toString())
                }?:run{
                    progressBar.dismiss()
                }
            })
            orderViewModel.apiFailLiveData.observeOnce(this@MainActivity, Observer {
                message->
                message?.let {
                    visibility = false
                    dataBinding.isVisible = visibility
                    progressBar.dismiss()
                    this@MainActivity.showToast(message)
                }?:run{
                    progressBar.dismiss()
                }
            })

        }
    }

    private fun insertCrustAndSizeList(
        crustLists: ArrayList<Crust>,
        sizeList: ArrayList<Size>
    ) {
        val repo = CustomizeRepo(dao = database.customPizzaDao)
        GlobalScope.launch(Dispatchers.IO) {
           orderViewModel.insertCrustAndSize(repo,crustLists,sizeList)
        }
    }

    private val onAddClickListener = View.OnClickListener {
        val fm = this.supportFragmentManager
        val customizePizzaDialog = CustomizePizzaDialog.newInstance(crustLists,defaultCrust)
        customizePizzaDialog.retainInstance = true
        customizePizzaDialog.showNow(fm, CUSTOMIZE_PIZZA_DIALOG)

    }

    private val onRemoveClickListener = View.OnClickListener {

    }

    private fun setToolbar(){
        setSupportActionBar(dataBinding.toolbar)
        title = CLASS_SIMPLE_NAME

    }

    companion object{
        const val CLASS_SIMPLE_NAME = "Customize Order"
        const val CUSTOMIZE_PIZZA_DIALOG = "Customize_Pizza"
    }
}
