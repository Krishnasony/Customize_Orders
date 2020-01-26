package i.krishnasony.customizeorders.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Orders(
    @SerializedName("crusts")
    var crusts: List<Crust?>?,
    @SerializedName("defaultCrust")
    var defaultCrust: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("isVeg")
    var isVeg: Boolean?,
    @SerializedName("name")
    var name: String?
):Parcelable {
    @Parcelize
    data class Crust(
        @SerializedName("defaultSize")
        var defaultSize: Int?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("sizes")
        var sizes: List<Size?>?
    ):Parcelable {
        @Parcelize
        data class Size(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("price")
            var price: Double?
        ):Parcelable
    }
}