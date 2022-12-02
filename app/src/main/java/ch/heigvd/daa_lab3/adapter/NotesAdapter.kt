package ch.heigvd.daa_lab3.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.models.NoteAndSchedule
import ch.heigvd.daa_lab3.models.State
import ch.heigvd.daa_lab3.models.Type

class NotesAdapter(items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var currentSortType = SortType.CREATION_DATE
    var items: List<NoteAndSchedule> = items
        set(value) {
            val sortedValue = sort(value)
            val diffCallback = NotesDiffCallback(items, sortedValue)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = sortedValue
            diffItems.dispatchUpdatesTo(this)
        }

    enum class SortType {
        CREATION_DATE, ETA
    }

    private fun sort(values: List<NoteAndSchedule>): List<NoteAndSchedule> {
        return values.sortedByDescending {
            when (currentSortType) {
                SortType.CREATION_DATE -> it.note.creationDate
                SortType.ETA -> it.schedule?.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val typeIcon = view.findViewById<ImageView>(R.id.item_note_icon_type)
        private val title = view.findViewById<TextView>(R.id.item_note_title)
        private val content = view.findViewById<TextView>(R.id.item_note_content)
        private val scheduleIcon = view.findViewById<ImageView>(R.id.item_note_schedule_icon)
        private val scheduleText = view.findViewById<TextView>(R.id.item_note_schedule_text)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            val note = noteAndSchedule.note
            val schedule = noteAndSchedule.schedule

            typeIcon.setImageResource(
                when (note.type) {
                    Type.TODO -> R.drawable.todo
                    Type.FAMILY -> R.drawable.family
                    Type.SHOPPING -> R.drawable.shopping
                    Type.WORK -> R.drawable.work
                    Type.NONE -> R.drawable.note
                }
            )

            typeIcon.setColorFilter(
                when (note.state) {
                    State.DONE -> Color.GREEN
                    State.IN_PROGRESS -> Color.GRAY
                }
            )

            title.text = note.title
            content.text = note.text

            if (schedule != null) {
                //TODO difference
//                Calendar.getInstance().min
//                val difference = LocalDate.now().until(LocalDate.from(schedule.date.toInstant()))
                scheduleText.text = schedule.date.toString()
                scheduleIcon.setColorFilter(
                    when (true) {
                        true -> Color.RED
                        else -> Color.GRAY
                    }
                )
            }
        }
    }
}

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
                && old == new
//                && old.note.state == new.note.state
    }
}