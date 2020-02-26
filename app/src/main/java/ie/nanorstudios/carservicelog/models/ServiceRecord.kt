package ie.nanorstudios.carservicelog.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "service_records_table")
class ServiceRecord(var date: String? = null) : Parcelable {
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Long = 0
}