package i.krishnasony.customizeorders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R

class CustomizePizzaDialog:DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.layout_customize_pizza, container)
        var pioneers = arrayOf("Dennis Ritchie", "Rodney Brooks", "Sergey Brin", "Larry Page", "Cynthia Breazeal", "Jeffrey Bezos", "Berners-Lee Tim", "Centaurus A", "Virgo Stellar Stream")

        val myListView = rootView.findViewById(R.id.crustRecyclerView) as RecyclerView
        //with arrayadapter you have to pass a textview as a resource, and that is simple_list_item_1

        this.dialog?.setTitle("Tech Pioneers")

        return rootView
    }
}