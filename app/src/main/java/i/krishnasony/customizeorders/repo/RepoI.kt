package i.krishnasony.customizeorders.repo

import i.krishnasony.customizeorders.data.Orders
import i.krishnasony.customizeorders.network.ApiCallback

interface RepoI {
    fun getOrders(apiCallback: ApiCallback<Orders>)
}