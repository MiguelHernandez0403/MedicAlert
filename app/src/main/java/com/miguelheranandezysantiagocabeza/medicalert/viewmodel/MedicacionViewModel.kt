package com.miguelheranandezysantiagocabeza.medicalert.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.MedicacionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MedicacionViewModel(
    private val repo: MedicacionRepository
) : ViewModel() {

    // ===========================
    // MEDICACIONES EN TIEMPO REAL
    // ===========================

    private val _medicaciones = MutableStateFlow<List<MedicacionEntity>>(emptyList())
    val medicaciones: StateFlow<List<MedicacionEntity>> = _medicaciones.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { lista ->
                _medicaciones.value = lista
            }
        }
    }

    // Obtener 1 medicamento
    fun obtenerPorId(id: Int?): Flow<MedicacionEntity?> {
        return if (id == null || id == -1) {
            MutableStateFlow(null)
        } else {
            repo.getByIdFlow(id)
        }
    }

    // ------------------------------------------
    // Conversión "hh:mm AM/PM" -> millis de HOY
    // ------------------------------------------
    private fun convertirAHoraMillis(hora: String): Long {
        return try {
            val formatoCompleto = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val fechaFinal = "$fechaActual $hora"
            formatoCompleto.parse(fechaFinal)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }

    // Insertar medicamento
    fun insertar(nombre: String, dosis: String, hora: String, frecuencia: Int, imagen: String?) {
        viewModelScope.launch {

            val millis = convertirAHoraMillis(hora)

            val nueva = MedicacionEntity(
                nombre = nombre,
                dosis = dosis,
                hora = hora,
                horaMillis = millis,
                frecuenciaHoras = frecuencia,
                imagen = imagen
            )

            repo.insert(nueva)
        }
    }

    // Actualizar medicamento
    fun actualizar(id: Int, nombre: String, dosis: String, hora: String, frecuencia: Int, imagen: String?) {
        viewModelScope.launch {

            val millis = convertirAHoraMillis(hora)

            val data = MedicacionEntity(
                id = id,
                nombre = nombre,
                dosis = dosis,
                hora = hora,
                horaMillis = millis,
                frecuenciaHoras = frecuencia,
                imagen = imagen
            )

            repo.update(data)
        }
    }

    // Eliminar medicamento
    fun eliminar(id: Int) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }

    // ===========================
    // HISTORIAL (SOLO LECTURA)
    // ===========================

    val historial: StateFlow<List<HistorialEntity>> =
        repo.getHistorial().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun historialPorMedicacion(idMedicacion: Int): Flow<List<HistorialEntity>> {
        return repo.getHistorialPorMedicacion(idMedicacion)
    }
}
