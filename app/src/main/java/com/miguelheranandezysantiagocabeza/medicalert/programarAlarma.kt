package com.miguelheranandezysantiagocabeza.medicalert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

fun programarAlarma(
    context: Context,
    id: Int,
    horaEnMilis: Long,
    titulo: String,
    mensaje: String,
    tipo: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("id", id)
        putExtra("titulo", titulo)
        putExtra("mensaje", mensaje)
        putExtra("tipo", tipo)
    }

    val pending = PendingIntent.getBroadcast(
        context,
        id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        horaEnMilis,
        pending
    )
}
