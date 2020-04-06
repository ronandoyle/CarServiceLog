package ie.nanorstudios.carservicelog.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ie.nanorstudios.carservicelog.converters.ServiceItemTypeConverter

@Entity(tableName = "service_records_table")
class ServiceRecord() : Parcelable {
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Long = 0

	var type: String? = null
	var date: String? = null
	@field:TypeConverters(ServiceItemTypeConverter::class)
	var serviceItems: MutableList<ServiceItem>? = null
	var mileage: String? = null

	constructor(parcel: Parcel) : this() {
		id = parcel.readLong()
		type = parcel.readString()
		date = parcel.readString()
		serviceItems = parcel.createTypedArrayList(ServiceItem.CREATOR)
		mileage = parcel.readString()
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeLong(id)
		parcel.writeString(type)
		parcel.writeString(date)
		parcel.writeTypedList(serviceItems)
		parcel.writeString(mileage)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ServiceRecord> {
		override fun createFromParcel(parcel: Parcel): ServiceRecord {
			return ServiceRecord(parcel)
		}

		override fun newArray(size: Int): Array<ServiceRecord?> {
			return arrayOfNulls(size)
		}
	}
}