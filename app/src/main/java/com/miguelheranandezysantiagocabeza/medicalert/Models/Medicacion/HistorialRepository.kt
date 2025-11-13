package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import android.content.Context
import kotlinx.coroutines.flow.Flow

class HistorialRepository private constructor(context: Context) {

    private val historialDao = MedicacionDatabase.getInstance(context).historialDao()

    fun getHistorial(): Flow<List<HistorialEntity>> = historialDao.getAll()

    fun getHistorialPorMedicacion(id: Int): Flow<List<HistorialEntity>> =
        historialDao.getByMedicacion(id)

    suspend fun insertarHistorial(item: HistorialEntity) =
        historialDao.insert(item)

    suspend fun borrarHistorial(id: Int) =
        historialDao.delete(id)
}