package i.krishnasony.customizeorders.network

import i.krishnasony.customizeorders.data.Orders
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("pizzas/1")
    fun getProductList(): Call<Orders>
}