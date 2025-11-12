package com.miguelheranandezysantiagocabeza.medicalert.data.local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miguelheranandezysantiagocabeza.medicalert.data.local.Entities.MedicacionEntity

@Dao
interface MedicacionDao {

    // READ - Muestra los datos de la tabla
    @Query("SELECT * FROM medicacion ORDER BY nombre ASC")
    suspend fun getAll(): List<MedicacionEntity>

    // READ - Muestra un solo dato especifico de la tabla usando el ID
    @Query("SELECT * FROM medicacion WHERE id = :id")
    suspend fun getById(id: Int): MedicacionEntity?

    // CREATE - Inserta los datos en la tabla
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicacion(medicacion: MedicacionEntity)

    // UPDATE - Actualiza datos especificos en la tabla usando el objeto completo
    @Update
    suspend fun updateMedicacion(medicacion: MedicacionEntity)

    // DELETE - Borra datos especificos de la tabla usando el ID
    @Delete
    suspend fun deleteMedicacion(medicacion: MedicacionEntity)

}