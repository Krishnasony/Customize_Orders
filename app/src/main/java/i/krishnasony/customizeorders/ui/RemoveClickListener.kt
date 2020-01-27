package i.krishnasony.customizeorders.ui

import i.krishnasony.customizeorders.room.entity.CustomPizza

interface RemoveClickListener {
    fun onItemRemoved(customPizza: CustomPizza,pos:Int)
}