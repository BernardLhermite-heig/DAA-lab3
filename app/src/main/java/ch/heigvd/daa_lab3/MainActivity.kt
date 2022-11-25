package ch.heigvd.daa_lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var btnAddNote : Button
    private lateinit var btnDeleteAllNotes : Button
    private lateinit var btnFilterDate : Button
    private lateinit var btnFilterETA : Button

    private val myViewModel : NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init(){
        btnAddNote = findViewById(R.id.menu_generate)
        btnDeleteAllNotes = findViewById(R.id.menu_delete_all)
        btnFilterDate = findViewById(R.id.menu_sort_creation_date)
        btnFilterETA = findViewById(R.id.menu_sort_eta)
    }

    private fun initListeners(){
        btnAddNote.setOnClickListener {
            myViewModel.generateANote()
        }
        btnDeleteAllNotes.setOnClickListener {
            myViewModel.deleteAllNote()
        }
        btnFilterDate.setOnClickListener {
            //myViewModel.filterByDate()
        }
        btnFilterETA.setOnClickListener {
            //myViewModel.filterByETA()
        }
    }
}