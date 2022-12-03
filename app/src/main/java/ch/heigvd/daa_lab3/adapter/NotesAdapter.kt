package ch.heigvd.daa_lab3.adapter

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_lab3.R
import ch.heigvd.daa_lab3.models.NoteAndSchedule
import ch.heigvd.daa_lab3.models.State
import ch.heigvd.daa_lab3.models.Type
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Adapter permettant de gérer une liste de notes.
 * La liste peut être triée grâce à la propriété [sortedBy].
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class NotesAdapter(items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var items: List<NoteAndSchedule> = items
        set(value) {
            val sortedValue = sort(value)
            val diffCallback = NotesDiffCallback(items, sortedValue)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = sortedValue
            diffItems.dispatchUpdatesTo(this)
        }
    var sortedBy = SortType.CREATION_DATE
        set(value) {
            if (field != value) {
                field = value
                items = items // On force le tri
            }
        }

    enum class SortType {
        CREATION_DATE, ETA
    }

    /**
     * Trie la liste en fonction de la valeur actuelle de sortedBy
     * @param values La liste à trier
     * @return La liste triée
     */
    private fun sort(values: List<NoteAndSchedule>): List<NoteAndSchedule> {
        return values.sortedByDescending {
            when (sortedBy) {
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

            title.text = note.title
            content.text = note.text

            typeIcon.setColorFilter(
                ContextCompat.getColor(
                    typeIcon.context,
                    when (note.state) {
                        State.IN_PROGRESS -> R.color.note_in_progress
                        State.DONE -> R.color.note_done
                    }
                )
            )

            if (schedule != null) {
                val (text, color) = getScheduleTextAndColor(schedule.date, scheduleIcon.context)
                scheduleText.text = text
                scheduleIcon.setColorFilter(color)
                scheduleIcon.visibility = View.VISIBLE
                scheduleText.visibility = View.VISIBLE
            } else {
                scheduleIcon.visibility = View.GONE
                scheduleText.visibility = View.GONE
            }
        }

        /**
         * Retourne le texte et la couleur à afficher pour une date de schedule donnée.
         * @param scheduleDate La date du schedule
         * @param context Un context pour récupérer les couleurs
         * @return Le texte et la couleur à afficher
         */
        private fun getScheduleTextAndColor(
            scheduleDate: Calendar,
            context: Context
        ): Pair<String, Int> {
            if (scheduleDate.before(Calendar.getInstance())) {
                return context.getString(R.string.schedule_late) to
                        ContextCompat.getColor(context, R.color.schedule_late)
            }

            val color = ContextCompat.getColor(context, R.color.schedule_in_time)
            val today = LocalDateTime.now()
            val targetDate = LocalDateTime.ofInstant(
                scheduleDate.toInstant(),
                TimeZone.getDefault().toZoneId()
            )
            val formatter =
                MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)

            val units = arrayOf(
                ChronoUnit.MONTHS,
                ChronoUnit.WEEKS,
                ChronoUnit.DAYS,
                ChronoUnit.HOURS,
                ChronoUnit.MINUTES
            )

            for (unit in units) {
                val delta = unit.between(today, targetDate)
                if (delta <= 0) {
                    continue
                }
                return formatter.formatMeasures(Measure(delta, unit.toMeasureUnit())) to color
            }

            return context.getString(R.string.schedule_now) to color
        }

        /**
         * Convertit un ChronoUnit en MeasureUnit
         * @throws UnsupportedOperationException si l'unité désirée n'est pas supportée
         */
        private fun ChronoUnit.toMeasureUnit(): MeasureUnit {
            return when (this) {
                ChronoUnit.MONTHS -> MeasureUnit.MONTH
                ChronoUnit.WEEKS -> MeasureUnit.WEEK
                ChronoUnit.DAYS -> MeasureUnit.DAY
                ChronoUnit.HOURS -> MeasureUnit.HOUR
                ChronoUnit.MINUTES -> MeasureUnit.MINUTE
                else -> throw UnsupportedOperationException("Unsupported unit $this")
            }
        }
    }
}

