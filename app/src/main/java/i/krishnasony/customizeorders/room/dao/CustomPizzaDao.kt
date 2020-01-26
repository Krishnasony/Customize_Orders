package i.krishnasony.customizeorders.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import i.krishnasony.customizeorders.room.entity.CustomPizza

@Dao
interface CustomPizzaDao {
    @Insert
    fun insertCutomizePizza(vararg customPizza: CustomPizza)

    @Query("SELECT * FROM custom_pizza WHERE item_id LIKE :itemId")
    fun getCustomizeData(itemId:String):LiveData<List<CustomPizza>>
}