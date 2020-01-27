package i.krishnasony.customizeorders.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.repo.CustomizeRepo
import i.krishnasony.customizeorders.room.database.AppDataBase
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.Size
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
    private var adapter:CustomizePizzaAdapter ? = null
    private var sizeAdapter:SizeAdapter ? = null
    private val database:AppDataBase by inject()
    private val orderViewModel: OrderViewModel by viewModel()
    private var crust = Crust()
    private var size = Size()
    private var defaultCrust= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            crustList = it.getParcelableArrayList<Crust>(CRUST) as ArrayList<Crust>
            defaultCrust = it.getString(DEFAULT_CRUST) as String
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.layout_customize_pizza, container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crustRv = rootView.findViewById<RecyclerView>(R.id.crustRecyclerView)
        val sizeRv = rootView.findViewById<RecyclerView>(R.id.sizeRecyclerView)
        sizeAdapter = SizeAdapter(this@CustomizePizzaDialog,context!!,sizeList,checkId = crust.defaultSize.toString())
        sizeRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        sizeRv.adapter = sizeAdapter
        adapter = CustomizePizzaAdapter(this,context!!,crustList,checkId = defaultCrust)
        crustRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        crustRv.adapter = adapter
        crust.id = defaultCrust
        getSizesForCrusts()
    }


    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window?.setLayout(width, height)
        }
    }

    override fun onRadioButtonClicked(crust: Crust) {
        this.crust =crust
        sizeList.clear()
        adapter?.notifyDataSetChanged()
        getSizesForCrusts()
    }

    private fun getSizesForCrusts(){
        val repo = CustomizeRepo(dao = database.customPizzaDao)
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

        fun newInstance(crust: ArrayList<Crust>,defaultCrustCheck:String): CustomizePizzaDialog {
            val f = CustomizePizzaDialog()

            // Supply crust input as an argument.
            val args = Bundle()
            args.putParcelableArrayList(CRUST, crust)
            args.putString(DEFAULT_CRUST,defaultCrustCheck)
            f.arguments = args

            return f
        }
    }
}