package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    // Insertar registro de toma
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: HistorialEntity)

    // Obtener TODO el historial ordenado por fecha y hora descendente
    @Query("""SELECT * FROM historial ORDER BY fecha DESC, id DESC""")
    fun getAll(): Flow<List<HistorialEntity>>

    // Obtener historial de un medicamento específico
    @Query("""SELECT * FROM historial WHERE idMedicacion = :id ORDER BY fecha DESC, id DESC""")
    fun getByMedicacion(id: Int): Flow<List<HistorialEntity>>

    // Borrar un registro individual
    @Query("DELETE FROM historial WHERE id = :id")
    suspend fun delete(id: Int)

    // Borrar TODO (para pruebas)
    @Query("DELETE FROM historial")
    suspend fun clear()
}