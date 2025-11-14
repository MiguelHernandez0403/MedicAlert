package com.miguelheranandezysantiagocabeza.medicalert.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.View.AddEditMediScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.HistoryScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.AddEditCitaScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.ChargeScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.CitasScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.DetallesMedicacionScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.MedicacionesScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.WelcomeScreen
import com.miguelheranandezysantiagocabeza.medicalert.ViewModel.MedicacionViewModel
import com.miguelheranandezysantiagocabeza.medicalert.viewmodel.CitasViewModel
import kotlinx.coroutines.delay


@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Charge",
        modifier = Modifier.fillMaxSize()
    ) {
        composable (route = "Charge") {
            ChargeScreenWithNavigation(
                onLoadingComplete = {
                    navController.navigate("Welcome") {
                        popUpTo("Charge") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "Welcome"){
            WelcomeScreen(
                onCitas = { navController.navigate("Citas") },
                onMedicaciones = { navController.navigate("Medicacion") }
            )
        }

        composable("Medicacion") {

            val app = LocalContext.current.applicationContext as MedicAlertApplication

            val vm: MedicacionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MedicacionViewModel(app.medicacionRepository) as T
                    }
                }
            )

            val lista by vm.medicaciones.collectAsState()

            MedicacionesScreen(
                medicaciones = lista,
                onEditMedicacion = { id ->
                    navController.navigate("AddEditMedi?id=$id")
                },
                onDetallesMedicacion = { id ->
                    navController.navigate("Detalle/$id")
                },
                onVolver = { navController.popBackStack() },
                onHistorial = { navController.navigate("Historial") },
                onAddMedicacion = {
                    navController.navigate("AddEditMedi?id=-1")
                }
            )
        }

        composable(
            route = "AddEditMedi?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = -1; type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: -1

            val app = LocalContext.current.applicationContext as MedicAlertApplication
            val viewModel: MedicacionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MedicacionViewModel(app.medicacionRepository) as T
                    }
                }
            )

            AddEditMediScreen(
                idMedicacion = id,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(route = "Historial"){
            HistoryScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "Detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetallesMedicacionScreen(
                idMedicacion = id,
                OnClickVolver = { navController.popBackStack() }
            )
        }

        composable(route = "Citas") {

            val app = LocalContext.current.applicationContext as MedicAlertApplication

            val vm: CitasViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return CitasViewModel(app.citasRepository) as T
                    }
                }
            )

            val lista by vm.citas.collectAsState()

            CitasScreen (
                citas = lista,
                OnClickEdit = { id ->
                    navController.navigate("AddEditCita?id=$id")
                },
                OnClickAgregar = {
                    navController.navigate("AddEditCita?id=-1")
                },
                OnclickVolver = { navController.popBackStack() }
            )
        }

        composable(route = "AddEditCita?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = -1; type = NavType.IntType })
        ) {backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: -1

            val app = LocalContext.current.applicationContext as MedicAlertApplication
            val viewModel: CitasViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return CitasViewModel(app.citasRepository) as T
                    }
                }
            )


            AddEditCitaScreen(
                idCita = id,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ChargeScreenWithNavigation(
    onLoadingComplete: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect (Unit) {
        delay(3000)
        isLoading = false
        onLoadingComplete()
    }

    ChargeScreen()
}