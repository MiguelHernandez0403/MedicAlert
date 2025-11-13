package com.miguelheranandezysantiagocabeza.medicalert.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MedicacionViewModel(
    private val repo: MedicacionRepository
) : ViewModel() {

    // --------------------------
    // MEDICAMENTOS EN TIEMPO REAL
    // --------------------------

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
            flow { emit(repo.getById(id)) }
        }
    }

    // Insertar medicamento
    fun insertar(nombre: String, dosis: String, hora: String, frecuencia: Int, imagen: String?) {
        viewModelScope.launch {
            val nueva = MedicacionEntity(
                nombre = nombre,
                dosis = dosis,
                hora = hora,
                frecuenciaHoras = frecuencia,
                imagen = imagen
            )
            repo.insert(nueva)
        }
    }

    // Actualizar medicamento
    fun actualizar(id: Int, nombre: String, dosis: String, hora: String, frecuencia: Int, imagen: String?) {
        viewModelScope.launch {
            val data = MedicacionEntity(
                id = id,
                nombre = nombre,
                dosis = dosis,
                hora = hora,
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

    // ============================================================
    //                    HISTORIAL
    // ============================================================

    // Obtener historial completo
    val historial: StateFlow<List<HistorialEntity>> =
        repo.getHistorial().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Obtener historial de un medicamento
    fun historialPorMedicacion(idMedicacion: Int): Flow<List<HistorialEntity>> {
        return repo.getHistorialPorMedicacion(idMedicacion)
    }

    // Registrar toma manual
    fun registrarToma(
        idMedicacion: Int,
        nombre: String,
        dosis: String,
        horaProgramada: String
    ) {
        viewModelScope.launch {

            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatoHora = SimpleDateFormat("hh:mm a", Locale.getDefault())

            val fechaActual = formatoFecha.format(Date())
            val horaActual = formatoHora.format(Date())

            val nuevo = HistorialEntity(
                idMedicacion = idMedicacion,
                nombre = nombre,
                dosis = dosis,
                horaProgramada = horaProgramada,
                horaTomada = horaActual,
                fecha = fechaActual,
                tipo = "manual"
            )

            repo.insertHistorial(nuevo)
        }
    }

    // Eliminar entrada del historial
    fun eliminarHistorial(id: Int) {
        viewModelScope.launch {
            repo.deleteHistorial(id)
        }
    }

    // Limpiar todo el historial (Ãºtil para pruebas)
    fun limpiarHistorial() {
        viewModelScope.launch {
            repo.clearHistorial()
        }
    }
}