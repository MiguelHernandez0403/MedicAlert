package com.miguelheranandezysantiagocabeza.medicalert.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.data.local.Entities.MedicacionEntity
import com.miguelheranandezysantiagocabeza.medicalert.domain.Repositories.MedicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicacionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicacionRepository

    private val _medicaciones = MutableStateFlow<List<MedicacionEntity>>(emptyList())
    val medicaciones: StateFlow<List<MedicacionEntity>> = _medicaciones

    init {
        val medicacionDao = (application as MedicAlertApplication).database.medicacionDao()
        repository = MedicacionRepository(medicacionDao)
        loadMedicaciones()
    }

    // CREATE
    fun insertMedicacion(medicacion: MedicacionEntity) {
        viewModelScope.launch {
            repository.insertMedicacion(medicacion)
            loadMedicaciones() // Recargar lista
        }
    }

    // READ
    private fun loadMedicaciones() {
        viewModelScope.launch {
            _medicaciones.value = repository.getAllMedicaciones()
        }
    }

    fun getMedicacionById(id: Int, onResult: (MedicacionEntity?) -> Unit) {
        viewModelScope.launch {
            val medicacion = repository.getMedicacionById(id)
            onResult(medicacion)
        }
    }

    // UPDATE
    fun updateMedicacion(medicacion: MedicacionEntity) {
        viewModelScope.launch {
            repository.updateMedicacion(medicacion)
            loadMedicaciones()
        }
    }

    // DELETE
    fun deleteMedicacion(id: Int) {
        viewModelScope.launch {
            repository.deleteMedicacion(id)
            loadMedicaciones()
        }
    }
}