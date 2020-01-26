package i.krishnasony.customizeorders.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.krishnasony.customizeorders.data.Orders
import i.krishnasony.customizeorders.network.ApiCallback
import i.krishnasony.customizeorders.repo.OrderRepo

class OrderViewModel:ViewModel() {
    val orderLiveData:MutableLiveData<Orders> = MutableLiveData()
    val apiFailLiveData:MutableLiveData<String> = MutableLiveData()

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
}