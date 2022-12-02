package ch.heigvd.daa_lab3.viewmodels

import androidx.lifecycle.ViewModel
import ch.heigvd.daa_lab3.database.DataRepository

class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    val allNotes = repository.allNotes //LiveData<List<NoteAndSchedule>>
    val countNotes = repository.notesCount //: LiveData<Int>

    fun generateANote() {
        repository.insertRandomNote()
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}