package ch.heigvd.daa_lab3.database

import ch.heigvd.daa_lab3.models.Note
import ch.heigvd.daa_lab3.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataRepository(
    private val noteDAO: NoteDAO,
    private val applicationScope: CoroutineScope
) {
    val allNotes = noteDAO.getAllNotes()
    val notesCount = noteDAO.getCount()

    fun insert(note: Note, schedule: Schedule?) {
        applicationScope.launch {
            val noteId = noteDAO.insert(note)

            if (schedule != null) {
                schedule.ownerId = noteId
                noteDAO.insert(schedule)
            }
        }
    }

    fun insertRandomNote() {
        applicationScope.launch {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()
            insert(note, schedule)
        }
    }
}