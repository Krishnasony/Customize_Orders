package i.krishnasony.customizeorders.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.room.entity.Crust





class CustomizePizzaDialog:AppCompatDialogFragment() {
    private lateinit var rootView: View
    private var crustList:ArrayList<Crust> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            crustList = it.getParcelableArrayList<Crust>(CRUST) as ArrayList<Crust>
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

    companion object {
        /**
         * Create a new instance of CustomizePizzaDialog, providing "num" as an
         * argument.
         */
        private const val CRUST = "crusts"
        fun newInstance(crust: ArrayList<Crust>): CustomizePizzaDialog {
            val f = CustomizePizzaDialog()

            // Supply crust input as an argument.
            val args = Bundle()
            args.putParcelableArrayList(CRUST, crust)
            f.arguments = args

            return f
        }
    }
}