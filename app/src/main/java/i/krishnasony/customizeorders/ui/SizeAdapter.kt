package i.krishnasony.customizeorders.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import i.krishnasony.customizeorders.R
import i.krishnasony.customizeorders.room.entity.Size
import kotlinx.android.synthetic.main.layout_recycler_item.view.*

class SizeAdapter(private var mView:ClickInterface,var context: Context, var sizeList:ArrayList<Size>, var checkId:String):RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {
    private var mLastSelectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        return SizeViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recycler_item,parent,false))

    }

    override fun getItemCount(): Int {
        return sizeList.size
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(sizeList[position],checkId,position,mView,mLastSelectedPosition)
    }

   inner  class SizeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(
            size: Size,
            checkId: String,
            position: Int,
            mView: ClickInterface,
            selectedPosition: Int
        ) {
            itemView.crust.text = size.name
            if ((selectedPosition == -1 && position == 0))
                itemView.crust.isChecked = true
            else
                itemView.crust.isChecked = selectedPosition == position


            itemView.crust.setOnClickListener {
                mView.onSizeRadioButtonClicked(size)
                mLastSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }

        }
    }
}