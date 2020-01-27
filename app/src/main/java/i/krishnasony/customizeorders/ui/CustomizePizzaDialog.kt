package i.krishnasony.customizeorders.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.MainActivity
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.repo.CustomizeRepo
import i.krishnasony.customizeorders.room.database.AppDataBase
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.room.entity.Size
import i.krishnasony.customizeorders.ui.adapter.CrustPizzaAdapter
import i.krishnasony.customizeorders.utils.observeOnce
import i.krishnasony.customizeorders.viewModel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class CustomizePizzaDialog:AppCompatDialogFragment(),ClickInterface {


    private lateinit var rootView: View
    private var crustList:ArrayList<Crust> = arrayListOf()
    private var sizeList:ArrayList<Size> = arrayListOf()
    private var adapter: CrustPizzaAdapter? = null
    private var sizeAdapter:SizeAdapter ? = null
    private val database:AppDataBase by inject()
    private val orderViewModel: OrderViewModel by viewModel()
    private var crust = Crust()
    private var size = Size()
    private var defaultCrust= ""
    private var defaultSize = ""
    private lateinit var repo: CustomizeRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            crustList = it.getParcelableArrayList<Crust>(CRUST) as ArrayList<Crust>
            defaultCrust = it.getString(DEFAULT_CRUST) as String
            defaultSize = it.getString(DEFAULT_SIZE) as String
            repo = CustomizeRepo(database.customPizzaDao)
            getCrustAndSize()

        }
    }

    private fun getCrustAndSize() {
        GlobalScope.launch (Dispatchers.Main){
            orderViewModel.getCrust(crustId = defaultCrust,repo = repo)
            orderViewModel.getSize(sizeId = defaultSize,repo = repo)
            orderViewModel.crustLiveData.observeOnce(this@CustomizePizzaDialog, Observer {
                crust->
                crust?.let {
                    this@CustomizePizzaDialog.crust = crust
                }
            })
            orderViewModel.sizeLiveData.observeOnce(this@CustomizePizzaDialog, Observer {
                    size->
                size?.let {
                    this@CustomizePizzaDialog.size = size
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.layout_customize_pizza, container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getSizesForCrusts()
    }

    private fun initView() {
        val crustRv = rootView.findViewById<RecyclerView>(R.id.crustRecyclerView)
        val sizeRv = rootView.findViewById<RecyclerView>(R.id.sizeRecyclerView)
        val addToCart = rootView.findViewById<AppCompatButton>(R.id.add)
        val cancel = rootView.findViewById<AppCompatButton>(R.id.cancel)
        addToCart.setOnClickListener {
            addCustomPizza()
            dialog?.dismiss()
        }
        cancel.setOnClickListener {
            dialog?.dismiss()
        }
        sizeAdapter = SizeAdapter(this@CustomizePizzaDialog,context!!,sizeList,checkId = crust.defaultSize.toString())
        sizeRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        sizeRv.adapter = sizeAdapter
        adapter = CrustPizzaAdapter(
            this,
            context!!,
            crustList,
            checkId = defaultCrust
        )
        crustRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        crustRv.adapter = adapter
        crust.id = defaultCrust
        crust.defaultSize = if (defaultSize.isNotEmpty()) defaultSize.toInt() else 0
    }

    private fun addCustomPizza() {
        val customPizza =  CustomPizza(itemId = "1",crustName = crust.name,price = size.price,sizeName = size.name,quantity = 1)
        val newCustomPizza = database.customPizzaDao.checkCustomPizzaExist(customPizza.crustName,customPizza.sizeName,customPizza.price)
        GlobalScope.launch(Dispatchers.IO) {
            newCustomPizza?.let {
                  database.customPizzaDao.updateCustomPizza(newCustomPizza.copy(quantity = newCustomPizza.quantity+1))
            }?:run{
                orderViewModel.insertCustomPizza(repo,customPizza = customPizza)
            }
        }
        (activity as MainActivity).getCustomPizza()
    }


    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            d.window?.setLayout(width, height)
        }
    }

    override fun onRadioButtonClicked(crust: Crust) {
        this.crust =crust
        sizeList.clear()
        getSizesForCrusts()
    }

    private fun getSizesForCrusts(){
        GlobalScope.launch(Dispatchers.Main) {
            orderViewModel.getSizesOfCrust(repo,crust.id)
            orderViewModel.sizelist.observeOnce(this@CustomizePizzaDialog, Observer {
                    list->
                list?.let {
                    sizeList = it  as ArrayList<Size>
                    sizeAdapter?.sizeList = sizeList
                    sizeAdapter?.checkId = crust.defaultSize.toString()
                    sizeAdapter?.notifyDataSetChanged()

                }
            })
        }
    }

    override fun onSizeRadioButtonClicked(size: Size) {
        this.size = size
    }

    companion object {
        /**
         * Create a new instance of CustomizePizzaDialog, providing "num" as an
         * argument.
         */
        private const val CRUST = "crusts"
        private const val DEFAULT_CRUST = "default_crust"
        private const val DEFAULT_SIZE = "default_size"


        fun newInstance(crust: ArrayList<Crust>,defaultCrustCheck:String,defaultSizeCheck: String): CustomizePizzaDialog {
            val f = CustomizePizzaDialog()

            // Supply crust input as an argument.
            val args = Bundle()
            args.putParcelableArrayList(CRUST, crust)
            args.putString(DEFAULT_CRUST,defaultCrustCheck)
            args.putString(DEFAULT_SIZE,defaultSizeCheck)

            f.arguments = args

            return f
        }
    }
}