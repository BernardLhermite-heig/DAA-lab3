package ch.heigvd.daa_lab3.adapter

import androidx.recyclerview.widget.DiffUtil
import ch.heigvd.daa_lab3.models.NoteAndSchedule

/**
 * Callback permettant de gérer les différences entre deux listes de notes.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class NotesDiffCallback(
    private val oldList: List<NoteAndSchedule>,
    private val newList: List<NoteAndSchedule>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}