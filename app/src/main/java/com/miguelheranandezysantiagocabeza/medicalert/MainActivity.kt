package com.miguelheranandezysantiagocabeza.medicalert

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.miguelheranandezysantiagocabeza.medicalert.Navigation.NavigationApp
import com.miguelheranandezysantiagocabeza.medicalert.ui.theme.MedicAlertTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crearCanalNotificaciones()
        pedirPermisoNotificaciones()

        enableEdgeToEdge()

        setContent {
            MedicAlertTheme {
                NavigationApp()
            }
        }
    }

    private fun crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val canal = NotificationChannel(
                "medicaciones_channel",
                "Recordatorios de Medicación y Citas",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }

    private fun pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }
}
