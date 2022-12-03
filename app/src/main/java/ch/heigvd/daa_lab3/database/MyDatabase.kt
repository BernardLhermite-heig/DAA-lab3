package ch.heigvd.daa_lab3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.daa_lab3.models.Note
import ch.heigvd.daa_lab3.models.Schedule
import kotlin.concurrent.thread

/**
 * Base de données contenant les notes et les schedules.
 * Des notes aléatoires sont insérés à l'ouverture de la DB si cette dernière est vide.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
@Database(
    entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(CalendarConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    companion object {
        private const val NB_NOTES_TO_CREATE_IF_EMPTY = 10

        private var INSTANCE: MyDatabase? = null

        /**
         * Retourne l'instance de la base de données.
         * @param context Le contexte de l'application
         * @return L'instance de la base de données
         */
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

        /**
         * Callback appelé à l'ouverture de la base de données pour y insérer des notes si besoin.
         */
        private fun populateCallBack(): Callback {
            return object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    INSTANCE?.let { database ->
                        if (db.query("SELECT * FROM Note").count > 0) {
                            return
                        }
                        
                        thread {
                            repeat(NB_NOTES_TO_CREATE_IF_EMPTY) {
                                val note = Note.generateRandomNote()
                                val schedule = Note.generateRandomSchedule()

                                val noteId = database.noteDAO().insert(note)
                                if (schedule != null) {
                                    schedule.ownerId = noteId
                                    database.noteDAO().insert(schedule)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}