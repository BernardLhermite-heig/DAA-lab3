package ch.heigvd.daa_lab3.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.daa_lab3.MyApp
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.fragments.NotesFragment
import ch.heigvd.daa_lab3.viewmodels.NotesViewModel
import ch.heigvd.daa_lab3.viewmodels.NotesViewModelFactory

/**
 * Activité principale de l'application. Les callback du menu sont définis ici.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class MainActivity : AppCompatActivity() {
    private val notesFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.notes_fragment) as NotesFragment
    }
    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_generate -> {
                viewModel.generateANote()
                true
            }
            R.id.menu_delete_all -> {
                viewModel.deleteAllNotes()
                true
            }
            R.id.menu_sort_eta -> {
                notesFragment.sortByEta()
                true
            }
            R.id.menu_sort_creation_date -> {
                notesFragment.sortByCreationDate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}