package ch.heigvd.daa_lab3.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Convertisseur pour le type Calendar dans la DB.
 *
 * @author Marengo Stéphane, Friedli Jonathan, Silvestri Géraud
 */
class CalendarConverter {
    @TypeConverter
    fun toCalendar(dateLong: Long): Calendar =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }

    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time // Long
}
