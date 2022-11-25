package ch.heigvd.daa_lab3

import android.app.Application
import ch.heigvd.daa_lab3.database.DataRepository
import ch.heigvd.daa_lab3.database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val repository by lazy {
        val database = MyDatabase.getDatabase(this)
        DataRepository(database.noteDAO(), applicationScope)
    }
}