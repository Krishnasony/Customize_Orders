package i.krishnasony.customizeorders.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "crust")
data class Crust(
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") var id: String,
    @ColumnInfo(name = "name") @SerializedName("name") var name: String,
    @ColumnInfo(name = "defaultSize") @SerializedName(value = "defaultSize") var defaultSize: Int
    ):Parcelable {
    constructor():this(
        id ="",
        name = "",
        defaultSize = 0
    )
}