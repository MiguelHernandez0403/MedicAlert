package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository

import android.content.Context
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import com.miguelheranandezysantiagocabeza.medicalert.programarAlarma
import kotlinx.coroutines.flow.Flow

class CitasRepository private constructor(private val context: Context) {

    private val citasDao = MedicacionDatabase.getInstance(context).citasDao()

    fun getCitas(): Flow<List<CitasEntity>> = citasDao.getAll()

    fun getCitasById(id: Int): Flow<CitasEntity?> = citasDao.getCitaById(id)

    // ----------------------------------------------------------
    // 🔥 INSERTAR CITA + OBTENER ID REAL + PROGRAMAR ALARMA
    // ----------------------------------------------------------
    suspend fun insertCita(cita: CitasEntity) {
        val idGenerado: Long = citasDao.insertCita(cita)
        val idInt = idGenerado.toInt()

        programarAlarma(
            context = context,
            id = idInt,
            horaEnMilis = cita.fechaHoraMillis,
            titulo = cita.titulo,
            mensaje = "Cita en ${cita.lugar}",
            tipo = "cita"
        )
    }

    // ----------------------------------------------------------
    // 🔥 ACTUALIZAR CITA Y REPROGRAMAR ALARMA
    // ----------------------------------------------------------
    suspend fun updateCita(cita: CitasEntity) {
        citasDao.updateCita(cita)

        programarAlarma(
            context = context,
            id = cita.id,
            horaEnMilis = cita.fechaHoraMillis,
            titulo = cita.titulo,
            mensaje = "Cita en ${cita.lugar}",
            tipo = "cita"
        )
    }

    suspend fun deleteCita(id: Int) = citasDao.deleteCita(id)

    companion object {
        @Volatile private var INSTANCE: CitasRepository? = null

        fun getInstance(context: Context): CitasRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CitasRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
