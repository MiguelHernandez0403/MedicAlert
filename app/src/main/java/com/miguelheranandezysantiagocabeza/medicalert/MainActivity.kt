package com.miguelheranandezysantiagocabeza.medicalert

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.miguelheranandezysantiagocabeza.medicalert.Navigation.NavigationApp
import com.miguelheranandezysantiagocabeza.medicalert.ui.theme.MedicAlertTheme

class MainActivity : ComponentActivity() {

    // Launcher para permiso de NOTIFICACIONES (Android 13+)
    private val requestNotifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        // Aquí podrías mostrar un mensaje si quieres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        NotificationHelper.crearCanal(this)

        solicitarPermisos()

        setContent {
            MedicAlertTheme {
                NavigationApp()
            }
        }
    }

    // ============================================================
    //  🔥 FUNCION QUE PIDE PERMISOS SEGUN VERSIÓN DE ANDROID
    // ============================================================
    private fun solicitarPermisos() {

        // ---------------------------
        // PERMISO DE NOTIFICACIONES
        // ---------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // ---------------------------
        // PERMISO EXACT ALARM
        // Android 12+
        // ---------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
    }
}