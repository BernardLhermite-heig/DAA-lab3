package ch.heigvd.daa_lab3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_lab3.MyApp
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.adapter.NotesAdapter
import ch.heigvd.daa_lab3.viewmodels.NotesViewModel
import ch.heigvd.daa_lab3.viewmodels.NotesViewModelFactory

/**
 * Fragment responsable de l'affichage de la liste des notes.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class NotesFragment : Fragment() {
    companion object {
        private const val SORTED_BY_KEY = "SORTED_BY_KEY"
    }

    private val notesAdapter: NotesAdapter by lazy { NotesAdapter() }
    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreSortType()

        view.findViewById<RecyclerView>(R.id.notes_list).apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.items = notes
        }
    }

    /**
     * Trie la liste des notes selon la date de leur schedule.
     */
    fun sortByEta() {
        NotesAdapter.SortType.ETA.let {
            notesAdapter.sortedBy = it
            saveSortType(it)
        }
    }

    /**
     * Trie la liste des notes selon leur date de création.
     */
    fun sortByCreationDate() {
        NotesAdapter.SortType.CREATION_DATE.let {
            notesAdapter.sortedBy = it
            saveSortType(it)
        }
    }

    /**
     * Restaure le type de tri si ce dernier existe dans les préférences.
     */
    private fun restoreSortType() {
        requireActivity().getPreferences(Context.MODE_PRIVATE)
            .getString(SORTED_BY_KEY, null)
            ?.let {
                notesAdapter.sortedBy = NotesAdapter.SortType.valueOf(it)
            }
    }

    /**
     * Sauvegarde le type de tri donné dans les préférences.
     * @param sortType le type de tri à sauvegarder.
     */
    private fun saveSortType(sortType: NotesAdapter.SortType) {
        requireActivity().getPreferences(Context.MODE_PRIVATE).edit {
            putString(SORTED_BY_KEY, sortType.name)
        }
    }
}