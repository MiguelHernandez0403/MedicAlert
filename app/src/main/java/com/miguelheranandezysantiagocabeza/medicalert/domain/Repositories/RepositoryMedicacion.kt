package com.miguelheranandezysantiagocabeza.medicalert.domain.Repositories

import com.miguelheranandezysantiagocabeza.medicalert.domain.Models.Medicacion
import kotlinx.coroutines.flow.Flow

interface MedicacionRepository{
    fun getMedicaciones(): Flow<List<Medicacion>>
    suspend fun insertMedicacion(medicacion: Medicacion)
    suspend fun deleteMedicacion(medicacion: Medicacion)
    suspend fun updateMedicacion(medicacion: Medicacion)


}