package i.krishnasony.customizeorders.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.room.entity.Size

@Dao
interface CustomPizzaDao {
    @Insert
    fun insertCustomPizza(vararg customPizza: CustomPizza)

    @Insert
    fun insertSize(vararg size: Size)

    @Insert
    fun insertCrust(vararg crust: Crust)

    @Query("SELECT id FROM crust GROUP BY id ORDER BY id DESC")
    fun getCrustId():String?

    @Delete
    fun delete(customPizza: CustomPizza)


    @Query("SELECT * FROM custom_pizza WHERE item_id LIKE :itemId")
    fun getCustomizeData(itemId:String):LiveData<List<CustomPizza>>

    @Query("SELECT * FROM size WHERE crust_id LIKE :crustId")
    fun getSizes(crustId:String):LiveData<List<Size>>
}