package com.miguelheranandezysantiagocabeza.medicalert

import android.app.Application
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.CitasRepository
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.MedicacionRepository

class MedicAlertApplication : Application() {

    val medicacionRepository: MedicacionRepository by lazy {
        MedicacionRepository.getInstance(this)
    }
    val citasRepository: CitasRepository by lazy {
        CitasRepository.getInstance(this)
    }
}