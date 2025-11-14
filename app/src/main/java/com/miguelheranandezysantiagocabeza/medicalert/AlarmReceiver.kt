package com.miguelheranandezysantiagocabeza.medicalert

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.CitasRepository
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.MedicacionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getIntExtra("id", -1)
        val tipo = intent.getStringExtra("tipo")
        val titulo = intent.getStringExtra("titulo") ?: "Recordatorio"
        val mensaje = intent.getStringExtra("mensaje") ?: ""

        val appContext = context.applicationContext

        val citasRepo = CitasRepository.getInstance(appContext)
        val medicacionRepo = MedicacionRepository.getInstance(appContext)

        // ------------------------------------------
        //  ⚡ Ejecutar la acción correspondiente
        // ------------------------------------------
        CoroutineScope(Dispatchers.IO).launch {

            when (tipo) {

                "cita" -> {
                    if (id != -1) citasRepo.deleteCita(id)
                }

                "medicacion" -> {
                    if (id != -1) {
                        val med = medicacionRepo.getById(id)
                        if (med != null) {
                            medicacionRepo.registrarToma(med)
                        }
                    }
                }
            }
        }

        // ------------------------------------------
        //  ⚡ Verificar permiso antes de notificar
        // ------------------------------------------
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return // Usuario NO dio permiso
        }

        // ------------------------------------------
        //  ⚡ Crear la notificación
        // ------------------------------------------
        val noti = NotificationCompat.Builder(context, "medicaciones_channel")
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), noti)
    }
}
