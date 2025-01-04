package com.application.x_pass.ui.inspector

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.application.x_pass.activity.LoginActivity
import com.application.x_pass.ui.QRScannerPermissionWrapper
import com.application.x_pass.ui.offline.BookingCheckScreen
import com.application.x_pass.utils.SPHelper
import com.application.x_pass.viewModel.InspectorViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspectorScreen(spHelper: SPHelper,
                    viewModel: InspectorViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState()?.value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentDestination != "qrScanner") { // Hide the bottom bar for "qrScanner"
                BottomNavigationBar(
                    currentDestination = currentDestination ?: "main_inspector",
                    onNavigateToMain = { navController.navigate("main_inspector") },
                    onNavigateToHistory = { navController.navigate("history_inspector") }
                )
            }
        },
        content = { paddingValues ->
            // Навигация по экранам
            NavHost(
                navController = navController,
                startDestination = viewModel.currentScreen,
                modifier = Modifier.padding(paddingValues)
            ) {
                // Экран сканера QR
                composable("qrScanner") {
                    QRScannerPermissionWrapper(goToBack = {
                        navController.popBackStack()
                    })
                }

                // Экран проверки по номеру бронирования
                composable("bookingCheck") {
                    BookingCheckScreen() // Убедитесь, что это отдельный экран
                }

                // Экран с меню
                composable("main_inspector") {
                    val context = LocalContext.current
                    MainInspectorScreen(
                        spHelper = spHelper,
                        onLogout = {
                            viewModel.clearSession()
                            logoutAndOpenLogin(context = context)
                        },
                        openScanner = {
                            navController.navigate("qrScanner")
                        }
                    )
                }


                // Экран с историей
                composable("history_inspector") {
                    InspectorHistoryScreen()
                }
            }
        }
    )
}

fun logoutAndOpenLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
    if (context is ComponentActivity) {
        context.finish()
    }
}