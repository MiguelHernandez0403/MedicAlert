package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitasDao {

    @Query("SELECT * FROM citas ORDER BY fechaHoraMillis DESC")
    fun getAll(): Flow<List<CitasEntity>>

    @Query("SELECT * FROM citas WHERE id = :id LIMIT 1")
    fun getCitaById(id: Int): Flow<CitasEntity?>

    // 🔥 Debe devolver Long para poder usar toInt() en el repositorio
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCita(cita: CitasEntity): Long

    @Update
    suspend fun updateCita(cita: CitasEntity)

    @Query("DELETE FROM citas WHERE id = :id")
    suspend fun deleteCita(id: Int)
}
