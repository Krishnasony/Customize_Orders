package i.krishnasony.customizeorders.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "size")
data class Size(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "primaryKeyId") @SerializedName("primaryKeyId")  val primaryKeyId:Int =0,
    @ColumnInfo(name = "id") @SerializedName("id") var id: String,
    @ColumnInfo(name = "name") @SerializedName("name") var name: String,
    @ColumnInfo(name = "price") @SerializedName(value = "price") var price: Double,
    @ColumnInfo(name = "crust_id") @SerializedName("crust_id") var crustId: String

    ):Parcelable {

    constructor():this(
        id="",
        name="",
        price=0.0,
        crustId =""
    )
}