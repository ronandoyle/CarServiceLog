package ie.nanorstudios.carservicelog.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.nanorstudios.carservicelog.models.ServiceRecord
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ServiceRecordDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(serviceRecord: ServiceRecord): Completable

	@Query("SELECT * from service_records_table")
	fun fetchServiceRecords(): Single<MutableList<ServiceRecord>>

	@Query("DELETE FROM service_records_table")
	fun deleteAllRecords()
}