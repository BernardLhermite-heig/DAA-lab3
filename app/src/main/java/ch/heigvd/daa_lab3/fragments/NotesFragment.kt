package ch.heigvd.daa_lab3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_lab3.MyApp
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.adapter.NotesAdapter
import ch.heigvd.daa_lab3.viewmodels.NotesViewModel
import ch.heigvd.daa_lab3.viewmodels.NotesViewModelFactory

class NotesFragment : Fragment() {
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

        view.findViewById<RecyclerView>(R.id.notes_list).apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.items = notes
        }
    }

    fun sortByEta() {
        notesAdapter.currentSortType = NotesAdapter.SortType.ETA
    }

    fun sortByCreationDate() {
        notesAdapter.currentSortType = NotesAdapter.SortType.CREATION_DATE
    }
}