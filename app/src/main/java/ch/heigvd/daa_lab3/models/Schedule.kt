package ch.heigvd.daa_lab3.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId: Long?,
    var ownerId: Long,
    var date: Calendar
)