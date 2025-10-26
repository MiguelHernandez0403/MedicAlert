package com.miguelheranandezysantiagocabeza.medicalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.miguelheranandezysantiagocabeza.medicalert.View.NavigationApp
import com.miguelheranandezysantiagocabeza.medicalert.ui.theme.MedicAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicAlertTheme {
                NavigationApp()
            }
        }
    }
}