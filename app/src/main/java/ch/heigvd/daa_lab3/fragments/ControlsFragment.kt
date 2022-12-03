package ch.heigvd.daa_lab3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.daa_lab3.MyApp
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.viewmodels.NotesViewModel
import ch.heigvd.daa_lab3.viewmodels.NotesViewModelFactory

/**
 * Fragment responsable de l'affichage des boutons de contrôle sur tablette.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class ControlsFragment : Fragment() {
    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.controls_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.generate_button).setOnClickListener {
            viewModel.generateANote()
        }

        view.findViewById<Button>(R.id.delete_all_button).setOnClickListener {
            viewModel.deleteAllNotes()
        }

        val counterText = view.findViewById<TextView>(R.id.nb_notes_text)
        viewModel.countNotes.observe(viewLifecycleOwner) { count ->
            counterText.text = count.toString()
        }
    }
}