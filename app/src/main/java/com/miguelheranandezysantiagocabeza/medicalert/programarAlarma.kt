package com.miguelheranandezysantiagocabeza.medicalert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

fun programarAlarma(
    context: Context,
    id: Int,
    horaEnMilis: Long,
    titulo: String,
    mensaje: String,
    tipo: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // ================================
    // 🔥 1. EVITAR QUE SE PROGRAME EN EL PASADO
    // ================================
    val ahora = System.currentTimeMillis()
    val tiempoFinal = if (horaEnMilis < ahora) {
        Log.w("ALARMA", "La hora está en el pasado. Ajustando a +60 segundos.")
        ahora + 60_000   // asegura que no crashee
    } else {
        horaEnMilis
    }

    // ================================
    // 🔥 2. CREAR INTENT
    // ================================
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("id", id)
        putExtra("titulo", titulo)
        putExtra("mensaje", mensaje)
        putExtra("tipo", tipo)
    }

    // ================================
    // 🔥 3. PENDING INTENT (REEMPLAZA ANTERIOR)
    // ================================
    val pending = PendingIntent.getBroadcast(
        context,
        id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // ================================
    // 🔥 4. SET EXACT ALARM (Android 6+)
    // ================================
    try {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            tiempoFinal,
            pending
        )
        Log.d("ALARMA", "Alarma programada correctamente → $tiempoFinal")
    } catch (e: SecurityException) {
        Log.e(
            "ALARMA",
            "ERROR: falta permiso SCHEDULE_EXACT_ALARM o USE_EXACT_ALARM"
        )
    }
}