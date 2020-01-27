package i.krishnasony.customizeorders.ui

import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.Size

interface ClickInterface {
    fun onRadioButtonClicked(crust: Crust)
    fun onSizeRadioButtonClicked(size: Size)
}