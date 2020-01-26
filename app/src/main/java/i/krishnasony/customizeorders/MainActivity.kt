package i.krishnasony.customizeorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import i.krishnasony.customizeorders.databinding.ActivityMainBinding
import i.krishnasony.customizeorders.network.ApiService
import i.krishnasony.customizeorders.repo.OrderRepo
import i.krishnasony.customizeorders.room.database.AppDataBase
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
        getDataFromApi()
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

    private val onAddClickListener = View.OnClickListener {
        if (visibility){
            val fm = supportFragmentManager
            val customizePizzaDialog = CustomizePizzaDialog()
            customizePizzaDialog.show(fm, CUSTOMIZE_PIZZA_DIALOG)
        }

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
