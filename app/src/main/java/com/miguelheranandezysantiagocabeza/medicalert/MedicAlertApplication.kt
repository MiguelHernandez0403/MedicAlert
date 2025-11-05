package com.miguelheranandezysantiagocabeza.medicalert

import android.app.Application
import androidx.room.Room
import com.miguelheranandezysantiagocabeza.medicalert.data.local.MedicAlertDataBase.MedicAlertDatabase

class MedicAlertApplication : Application() {

    val database: MedicAlertDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MedicAlertDatabase::class.java,
            "medic_alert_database"
        ).build()
    }
}