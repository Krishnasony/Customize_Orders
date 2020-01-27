package i.krishnasony.customizeorders.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "custom_pizza")
@Parcelize
data class CustomPizza(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") var id: Int=0,
    @ColumnInfo(name = "item_id") @SerializedName("item_id") var itemId: String,
    @ColumnInfo(name = "crust_name") @SerializedName("crust_name") var crustName: String,
    @ColumnInfo(name = "price") @SerializedName(value = "price") var price: Double,
    @ColumnInfo(name = "size_name") @SerializedName("size_name") var sizeName: String,
    @ColumnInfo(name = "quantity") @SerializedName(value = "quantity") var quantity: Int


    ): Parcelable {
    constructor() : this(
        itemId = "",
        crustName = "",
        price = 0.0,
        sizeName = "",
        quantity = 0
    )
}