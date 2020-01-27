package i.krishnasony.customizeorders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import i.krishnasony.customizeorders.R

class CustomPizzaListDialog : DialogFragment() {
    private lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.layout_pizza_list, container,false)
        return  rootView
    }

    companion object {
        fun newInstance():CustomPizzaListDialog{
            return CustomPizzaListDialog()
        }
    }
}