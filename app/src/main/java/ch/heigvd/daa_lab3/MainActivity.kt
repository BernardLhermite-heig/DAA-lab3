package ch.heigvd.daa_lab3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.daa_lab3.fragments.NotesFragment

class MainActivity : AppCompatActivity() {
    private val myViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.notes_fragment, NotesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_generate -> {
                myViewModel.generateANote()
                true
            }
            R.id.menu_delete_all -> {
                myViewModel.deleteAllNotes()
                true
            }
            R.id.menu_sort_eta -> {
//                myViewModel.()
                true
            }
            R.id.menu_sort_creation_date -> {
//                myViewModel.sortByCreationDate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}