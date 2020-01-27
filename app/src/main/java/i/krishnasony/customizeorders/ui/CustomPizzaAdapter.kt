package i.krishnasony.customizeorders.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.BR
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.room.entity.CustomPizza


class CustomPizzaAdapter(private val mView:RemoveClickListener, var context: Context, var customPizzaList:ArrayList<CustomPizza>):RecyclerView.Adapter<CustomPizzaAdapter.PizzaViewHolder>() {
    private var pos:Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        return PizzaViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_custom_pizza_recycler_item,parent,false))
    }

    override fun getItemCount(): Int {
        return customPizzaList.size
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        holder.bind(customPizzaList[position],position)
    }

    private val onRemoveClickListener = View.OnClickListener {
        mView.onItemRemoved(it.tag as CustomPizza,pos)
    }


    inner class PizzaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(
            customPizza: CustomPizza,
            position: Int
        ) {
            pos = position
            binding.setVariable(BR.customPizza,customPizza)
            binding.setVariable(BR.onRemove,onRemoveClickListener)

        }
        private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!


    }
}