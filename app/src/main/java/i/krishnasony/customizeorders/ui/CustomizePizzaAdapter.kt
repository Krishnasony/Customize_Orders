package i.krishnasony.customizeorders.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.room.entity.Crust
import kotlinx.android.synthetic.main.layout_recycler_item.view.*



class CustomizePizzaAdapter(private val mView:ClickInterface,var context: Context,var crustList:ArrayList<Crust>,var checkId:String):RecyclerView.Adapter<CustomizePizzaAdapter.PizzaViewHolder>() {
    private var mLastSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        return PizzaViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recycler_item,parent,false))
    }

    override fun getItemCount(): Int {
        return crustList.size
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        holder.bind(crustList[position],checkId,mView,mLastSelectedPosition,position)
    }


    inner class PizzaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(
            crust: Crust,
            checkId: String,
            mView: ClickInterface,
            lastSelectedPosition: Int,
            position: Int
        ) {
            itemView.crust.text = crust.name
            if ((lastSelectedPosition == -1 && position == 0))
                itemView.crust.isChecked = true
            else
                itemView.crust.isChecked = lastSelectedPosition == position

            itemView.crust.setOnClickListener {
                mView.onRadioButtonClicked(crust)
                mLastSelectedPosition = adapterPosition
                itemView.crust.isChecked = true
                notifyDataSetChanged()
            }
        }


    }
}