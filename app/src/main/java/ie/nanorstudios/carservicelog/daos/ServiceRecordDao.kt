package ie.nanorstudios.carservicelog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.nanorstudios.carservicelog.models.ServiceRecord

@Dao
interface ServiceRecordDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(serviceRecord: ServiceRecord)

	@Query("SELECT * from service_records_table")
	fun fetchServiceRecords(): LiveData<List<ServiceRecord>>

	@Query("DELETE FROM service_records_table")
	fun deleteAllRecords()
}