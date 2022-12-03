package ch.heigvd.daa_lab3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ch.heigvd.daa_lab3.models.Note
import ch.heigvd.daa_lab3.models.NoteAndSchedule
import ch.heigvd.daa_lab3.models.Schedule

/**
 * DAO permettant de manipuler les notes et les schedules.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
@Dao
interface NoteDAO {
    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule): Long

    @Query("SELECT COUNT(*) FROM Note")
    fun getCount(): LiveData<Long>

    @Transaction
    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Query("DELETE FROM Note")
    fun deleteAllNotes()
}