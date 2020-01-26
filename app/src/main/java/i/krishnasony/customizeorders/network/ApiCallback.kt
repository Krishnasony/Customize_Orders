package i.krishnasony.customizeorders.network

interface ApiCallback<T>{
    fun onSuccess(t: T)
    fun onFailure(message: String)
}