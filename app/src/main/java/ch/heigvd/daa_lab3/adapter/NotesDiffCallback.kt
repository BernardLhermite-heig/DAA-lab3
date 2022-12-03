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
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return old::class == new::class
                && old.note.state == new.note.state
                && old.note.title == new.note.title
                && old.note.text == new.note.text
                && old.note.type == new.note.type
                && old.note.creationDate == new.note.creationDate
                && old.schedule?.date == new.schedule?.date
    }
}