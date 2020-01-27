package i.krishnasony.customizeorders.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.room.entity.Crust
import kotlinx.android.synthetic.main.layout_recycler_item.view.*

class CustomizePizzaAdapter(var context: Context,var crustList:ArrayList<Crust>,var checkId:String):RecyclerView.Adapter<CustomizePizzaAdapter.PizzaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        return PizzaViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recycler_item,parent,false))
    }

    override fun getItemCount(): Int {
        return crustList.size
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        holder.bind(crustList[position],checkId)
    }

    class PizzaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(crust: Crust, checkId: String) {
            itemView.crust.text = crust.name
            if (checkId==crust.id){
                itemView.crust.isChecked = true
            }
        }

    }
}