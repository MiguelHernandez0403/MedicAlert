package com.miguelheranandezysantiagocabeza.medicalert

import android.app.Application
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionRepository

class MedicAlertApplication : Application() {

    val medicacionRepository: MedicacionRepository by lazy {
        MedicacionRepository.getInstance(this)
    }
}