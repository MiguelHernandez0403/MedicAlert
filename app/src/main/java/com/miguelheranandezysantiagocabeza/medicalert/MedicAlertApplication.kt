package com.miguelheranandezysantiagocabeza.medicalert

import android.app.Application
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.CitasRepository
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.MedicacionRepository

class MedicAlertApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val channel = android.app.NotificationChannel(
            "medicaciones_channel",
            "Recordatorios de Medicación y Citas",
            android.app.NotificationManager.IMPORTANCE_HIGH
        )

        val manager = getSystemService(android.app.NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    val medicacionRepository: MedicacionRepository by lazy {
        MedicacionRepository.getInstance(this)
    }

    val citasRepository: CitasRepository by lazy {
        CitasRepository.getInstance(this)
    }
}
