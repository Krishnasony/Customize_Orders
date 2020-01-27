package i.krishnasony.customizeorders.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import i.krishnasony.customizeorders.room.dao.CustomPizzaDao
import i.krishnasony.customizeorders.room.database.AppDataBase.Companion.VERSION
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.room.entity.Size

@Database(
    entities = [
     Crust::class,
     CustomPizza::class,
     Size::class
    ],
    version = VERSION,
    exportSchema = false
)
abstract class AppDataBase:RoomDatabase(){
    abstract val customPizzaDao:CustomPizzaDao
    companion object{
        const val CURRENT: String = "AppDataBase"
        const val VERSION = 1
        const val DATABASE_NAME = "custom_pizza.db"

    }
}