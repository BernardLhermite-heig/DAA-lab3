package ch.heigvd.daa_lab3

import androidx.lifecycle.ViewModel
import ch.heigvd.daa_lab3.database.DataRepository

class NotesViewModel(private val repository : DataRepository) : ViewModel() {

    private val allNotes = repository.allNotes //LiveData<List<NoteAndSchedule>>
    private val countNotes = repository.notesCount //: LiveData<Int>

    fun generateANote() {
        repository.insertRandomNote()
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }
}