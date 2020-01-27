package i.krishnasony.customizeorders.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.krishnasony.customizeorders.data.Orders
import i.krishnasony.customizeorders.network.ApiCallback
import i.krishnasony.customizeorders.repo.CustomizeRepo
import i.krishnasony.customizeorders.repo.OrderRepo
import i.krishnasony.customizeorders.room.entity.Crust
import i.krishnasony.customizeorders.room.entity.CustomPizza
import i.krishnasony.customizeorders.room.entity.Size

class OrderViewModel:ViewModel() {
    val orderLiveData:MutableLiveData<Orders> = MutableLiveData()
    val apiFailLiveData:MutableLiveData<String> = MutableLiveData()
    lateinit var customPizzaList:LiveData<List<CustomPizza>>
    lateinit var sizelist:LiveData<List<Size>>
    lateinit var crustLiveData:LiveData<Crust>
    lateinit var sizeLiveData: LiveData<Size>

    suspend fun getOrders(repoI: OrderRepo){
            repoI.getOrders(object:ApiCallback<Orders>{
                override fun onSuccess(t: Orders) {
                    orderLiveData.postValue(t)
                    apiFailLiveData.postValue(null)
                }

                override fun onFailure(message: String) {
                    apiFailLiveData.postValue(message)
                    orderLiveData.postValue(null)
                    Log.e("ERROR: ",message)
                }

            })
        }
    suspend fun insertCrustAndSize(repo: CustomizeRepo,crustList:ArrayList<Crust>,sizeList:ArrayList<Size>){
            crustList.forEach {
                repo.insertCrust(it)
            }

            sizeList.forEach {
                repo.insertSize(it)
            }
    }

    suspend fun getSizesOfCrust(repo: CustomizeRepo,crustId:String){
        sizelist = repo.getSizes(crustId)
    }

    suspend fun insertCustomPizza(repo: CustomizeRepo,customPizza: CustomPizza){
        repo.insertCustomPizza(customPizza)
    }

    suspend fun getCustomPizza(repo: CustomizeRepo){
        customPizzaList = repo.getCustomPizza()
    }

    suspend fun deleteCustomPizza(customPizza: CustomPizza,repo: CustomizeRepo){
        repo.deleteCustomPizza(customPizza)
    }

    suspend fun getCrust(crustId: String,repo: CustomizeRepo){
        crustLiveData = repo.getCrust(crustId)
    }

    suspend fun getSize(sizeId: String,repo: CustomizeRepo){
        sizeLiveData = repo.getSize(sizeId)
    }

}