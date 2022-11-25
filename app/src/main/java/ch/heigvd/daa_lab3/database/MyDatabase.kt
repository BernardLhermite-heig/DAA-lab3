package ch.heigvd.daa_lab3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.daa_lab3.models.Note
import ch.heigvd.daa_lab3.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(
    entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(CalendarConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    companion object {
        private const val NB_NOTES = 10

        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, "mydatabase.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(populateCallBack())
                    .build()
                INSTANCE!!
            }
        }

        private fun populateCallBack(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    INSTANCE?.let { database ->
                        val noteDAO = database.noteDAO()
                        val isEmpty = noteDAO.getCount().value == 0L

                        if (isEmpty) {
                            val repository =
                                DataRepository(noteDAO, CoroutineScope(SupervisorJob()))

                            repeat(NB_NOTES) {
                                repository.insertRandomNote()
                            }
                        }
                    }
                }
            }
        }
    }
}