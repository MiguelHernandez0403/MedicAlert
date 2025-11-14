package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository

import android.content.Context
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity
import com.miguelheranandezysantiagocabeza.medicalert.utils.millisAFecha
import com.miguelheranandezysantiagocabeza.medicalert.utils.millisAHora
import com.miguelheranandezysantiagocabeza.medicalert.programarAlarma
import kotlinx.coroutines.flow.Flow

class MedicacionRepository private constructor(private val context: Context) {

    private val db = MedicacionDatabase.getInstance(context)
    private val medicacionDao = db.medicacionDao()
    private val historialDao = db.historialDao()

    // ============================================================
    // MEDICACIÓN
    // ============================================================

    fun getAll(): Flow<List<MedicacionEntity>> = medicacionDao.getAll()

    suspend fun getById(id: Int) = medicacionDao.getById(id)

    fun getByIdFlow(id: Int): Flow<MedicacionEntity?> = medicacionDao.getByIdFlow(id)

    // ------------------------------------------------------------
    // 🔥 INSERTAR MEDICACIÓN + OBTENER ID REAL + PROGRAMAR ALARMA
    // ------------------------------------------------------------
    suspend fun insert(item: MedicacionEntity) {
        val idGenerado: Long = medicacionDao.insert(item)
        val idInt = idGenerado.toInt()

        programarAlarma(
            context = context,
            id = idInt,
            horaEnMilis = item.horaMillis,
            titulo = "Toma tu medicación",
            mensaje = "${item.nombre} (${item.dosis})",
            tipo = "medicacion"
        )
    }

    // ------------------------------------------------------------
    // 🔥 ACTUALIZAR MEDICACIÓN Y REPROGRAMAR ALARMA
    // ------------------------------------------------------------
    suspend fun update(item: MedicacionEntity) {
        medicacionDao.update(item)

        programarAlarma(
            context = context,
            id = item.id,
            horaEnMilis = item.horaMillis,
            titulo = "Toma tu medicación",
            mensaje = "${item.nombre} (${item.dosis})",
            tipo = "medicacion"
        )
    }

    suspend fun delete(id: Int) = medicacionDao.deleteById(id)

    // ============================================================
    // HISTORIAL
    // ============================================================

    fun getHistorial(): Flow<List<HistorialEntity>> = historialDao.getAll()

    fun getHistorialPorMedicacion(id: Int): Flow<List<HistorialEntity>> =
        historialDao.getByMedicacion(id)

    // ------------------------------------------------------------
    // 🔥 REGISTRAR TOMA AUTOMÁTICA DESDE EL RECEIVER
    // ------------------------------------------------------------
    suspend fun registrarToma(item: MedicacionEntity) {
        historialDao.insert(
            HistorialEntity(
                idMedicacion = item.id,
                nombre = item.nombre,
                dosis = item.dosis,
                horaProgramada = item.hora,
                horaTomada = millisAHora(System.currentTimeMillis()),
                fecha = millisAFecha(System.currentTimeMillis()),
                timestamp = System.currentTimeMillis(),
                tipo = "automatica"
            )
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: MedicacionRepository? = null

        fun getInstance(context: Context): MedicacionRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = MedicacionRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
