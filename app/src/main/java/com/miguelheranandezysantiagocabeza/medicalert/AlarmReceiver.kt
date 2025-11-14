package com.miguelheranandezysantiagocabeza.medicalert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.utils.FechaUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getIntExtra("id", -1)
        val titulo = intent.getStringExtra("titulo") ?: "Recordatorio"
        val mensaje = intent.getStringExtra("mensaje") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""

        // 🔥 Mostrar notificación SIEMPRE
        mostrarNotificacion(
            context = context,
            id = id,
            titulo = titulo,
            mensaje = mensaje
        )

        // 🔥 SOLO medicación → registrar historial automáticamente
        if (tipo == "medicacion") {
            val db = MedicacionDatabase.getInstance(context)
            val historialDao = db.historialDao()

            CoroutineScope(Dispatchers.IO).launch {
                val hoy = FechaUtils.obtenerFechaHoy()
                val horaActual = FechaUtils.obtenerHoraActual()

                val item = HistorialEntity(
                    idMedicacion = id,
                    nombre = titulo,
                    dosis = mensaje,
                    horaProgramada = horaActual,
                    horaTomada = horaActual,
                    fecha = hoy,
                    timestamp = System.currentTimeMillis(),
                    tipo = "auto"
                )

                historialDao.insert(item)
            }
        }
    }
}