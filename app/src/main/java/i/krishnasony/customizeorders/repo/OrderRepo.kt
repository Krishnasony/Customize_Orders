package i.krishnasony.customizeorders.repo

import i.krishnasony.customizeorders.data.Orders
import i.krishnasony.customizeorders.network.ApiCallback
import i.krishnasony.customizeorders.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepo(private val apiService: ApiService):RepoI {

    override suspend fun getOrders(apiCallback: ApiCallback<Orders>) {
       apiService.getProductList().enqueue(object :Callback<Orders>{
           override fun onFailure(call: Call<Orders>, t: Throwable) {
               apiCallback.onFailure(t.localizedMessage)
           }

           override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
               if (response.isSuccessful){
                   response.body()?.let {
                       apiCallback.onSuccess(it)
                   }
               }else{
                   apiCallback.onFailure("Something Went Wrong")
               }
           }

       })

    }
}