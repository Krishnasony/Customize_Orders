package i.krishnasony.customizeorders.repo

import i.krishnasony.customizeorders.room.dao.CustomPizzaDao
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.room.entity.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomizeRepo(var dao:CustomPizzaDao) {
    suspend fun insertCustomPizza(customPizza: CustomPizza) = withContext(Dispatchers.IO){
        dao.insertCustomPizza(customPizza)
    }

    suspend fun insertSize(size: Size) = withContext(Dispatchers.IO){
        dao.insertSize(size)
    }

    suspend fun insertCrust(crust: Crust) = withContext(Dispatchers.IO){
        dao.insertCrust(crust)
    }

    suspend fun getSizes(crustId:String) = withContext(Dispatchers.IO){
        dao.getSizes(crustId)
    }

    suspend fun getCustomPizza() = withContext(Dispatchers.IO){
        dao.getCustomizeData(itemId = "1")
    }

}