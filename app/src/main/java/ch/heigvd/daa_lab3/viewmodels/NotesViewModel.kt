package ch.heigvd.daa_lab3.viewmodels

import androidx.lifecycle.ViewModel
import ch.heigvd.daa_lab3.database.DataRepository

/**
 * ViewModel permettant de gérer les interactions avec les notes.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class NotesViewModel(private val repository: DataRepository) : ViewModel() {
    val allNotes = repository.allNotes //LiveData<List<NoteAndSchedule>>
    val countNotes = repository.notesCount //: LiveData<Int>

    /**
     * Ajoute une note aléatoire.
     */
    fun generateANote() {
        repository.insertRandomNote()
    }

    /**
     * Supprime l'ensemble des notes ainsi que les éventuels schedules associés.
     */
    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}