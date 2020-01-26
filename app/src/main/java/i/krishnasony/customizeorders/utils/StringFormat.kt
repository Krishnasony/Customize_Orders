package i.krishnasony.customizeorders.utils

import java.text.NumberFormat
import java.util.*

object StringFormat {
    @JvmStatic
    fun rupeeFormat(d:Double):String = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(d)

}