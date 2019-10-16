package ie.nanorstudios.carservicelog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ie.nanorstudios.carservicelog.daos.ServiceRecordDao
import ie.nanorstudios.carservicelog.models.ServiceRecord

private const val DB_NAME = "service_database"

@Database(entities = [ServiceRecord::class] , version = 1)
abstract class ServiceRecordDatabase: RoomDatabase() {

	abstract fun serviceRecordDao(): ServiceRecordDao

	companion object {
		@JvmStatic
		private var INSTANCE: ServiceRecordDatabase? = null

		fun getDatabase(context: Context): ServiceRecordDatabase {
			if (INSTANCE == null) {
				synchronized(ServiceRecordDatabase::class.java) {
					if (INSTANCE == null) {
						INSTANCE = Room.databaseBuilder(
							context.applicationContext,
							ServiceRecordDatabase::class.java, DB_NAME)
							.fallbackToDestructiveMigration()
							.build()
					}
				}
			}
			return INSTANCE!!
		}
	}
}