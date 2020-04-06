package ie.nanorstudios.carservicelog.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ServiceItem() : Parcelable {
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Long = 0

	var title: String? = null
	var price: String? = null

	constructor(parcel: Parcel) : this() {
		title = parcel.readString()
		price = parcel.readString()
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(title)
		parcel.writeString(price)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ServiceItem> {
		override fun createFromParcel(parcel: Parcel): ServiceItem = ServiceItem(parcel)

		override fun newArray(size: Int): Array<ServiceItem?> = arrayOfNulls(size)
	}
}