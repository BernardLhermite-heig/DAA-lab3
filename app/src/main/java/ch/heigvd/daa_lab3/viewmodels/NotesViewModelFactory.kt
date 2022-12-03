package ch.heigvd.daa_lab3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.daa_lab3.database.DataRepository

/**
 * Factory permettant de créer une instance de NotesViewModel avec les bons paramètres.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class NotesViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
