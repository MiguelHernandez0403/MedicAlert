package com.miguelheranandezysantiagocabeza.medicalert.data.local.Repositories

import com.miguelheranandezysantiagocabeza.medicalert.data.local.DAO.MedicacionDao
import com.miguelheranandezysantiagocabeza.medicalert.data.local.Entities.MedicacionEntity

class MedicacionRepository(private val medicacionDao: MedicacionDao) {

    suspend fun insertMedicacion(medicacion: MedicacionEntity) {
        medicacionDao.insert(medicacion)
    }

    suspend fun getAllMedicaciones(): List<MedicacionEntity> {
        return medicacionDao.getAll()
    }

    suspend fun getMedicacionById(id: Int): MedicacionEntity? {
        return medicacionDao.getById(id)
    }

    suspend fun updateMedicacion(medicacion: MedicacionEntity) {
        medicacionDao.update(medicacion)
    }

    suspend fun deleteMedicacion(id: Int) {
        medicacionDao.deleteById(id)
    }
}