package ch.heigvd.daa_lab3.database

import ch.heigvd.daa_lab3.models.Note
import ch.heigvd.daa_lab3.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Gère les interactions avec les données.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class DataRepository(
    private val noteDAO: NoteDAO,
    private val applicationScope: CoroutineScope
) {
    val allNotes = noteDAO.getAllNotes()
    val notesCount = noteDAO.getCount()

    /**
     * Ajoute une note possédant éventuellement un schedule.
     * @param note La note à insérer
     * @param schedule L'éventuel schedule associé à la note
     */
    fun insert(note: Note, schedule: Schedule?) {
        applicationScope.launch {
            val noteId = noteDAO.insert(note)

            if (schedule != null) {
                schedule.ownerId = noteId
                noteDAO.insert(schedule)
            }
        }
    }

    /**
     * Ajoute une note aléatoire.
     */
    fun insertRandomNote() {
        applicationScope.launch {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()
            insert(note, schedule)
        }
    }

    /**
     * Supprime l'ensemble des notes ainsi que les éventuels schedules associés.
     */
    fun deleteAllNotes() {
        applicationScope.launch {
            noteDAO.deleteAllNotes()
        }
    }
}