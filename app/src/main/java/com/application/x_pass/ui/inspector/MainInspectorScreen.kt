package com.application.x_pass.ui.inspector

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.application.x_pass.R
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.x_pass.data.remote.request_response.EventsResponse
import com.application.x_pass.ui.dialog.LogoutConfirmationDialog
import com.application.x_pass.utils.DateUtils.Companion.applyTimeShift
import com.application.x_pass.utils.DateUtils.Companion.formatDateString
import com.application.x_pass.utils.DateUtils.Companion.parseDateString
import com.application.x_pass.utils.SPHelper
import com.application.x_pass.viewModel.inspector.InspectorViewModel
@Composable
fun MainInspectorScreen(viewModel: InspectorViewModel = hiltViewModel(),
                        spHelper: SPHelper,
                        onLogout: () -> Unit,
                        openScanner: () -> Unit) {
    val isLoading by viewModel.isLoading
    val eventData by viewModel.eventData
    val errorMessage by viewModel.errorMessage
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.d("TOKEN REFRESH" , spHelper.getRefreshToken()+"")
        Log.d("TOKEN" , spHelper.getAccessToken()+"")

        viewModel.getEventInfo()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Toolbar
        toolBar(onLogoutClick = { showLogoutDialog = true })

        LineWithDropShadow()

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            eventData != null -> {
                spHelper.saveEventId(eventData!!.value[0].id)
                EventCard(eventData!!.value[0])
            }
            else -> {
                EmptyStateScreen()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(R.string.pleas_scan),
            fontFamily = robotoRegular,
            color = colorResource(R.color.auxiliary_70),
            modifier = Modifier.fillMaxWidth(), // Make text fill remaining space
            textAlign = TextAlign.Center
        )
        // Кнопка для загрузки данных
        TwoButtonsWithStyles(openScanner = openScanner)
    }
    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }
}


@Composable
fun TwoButtonsWithStyles(openScanner: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 38.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // First Button
        Button(
            onClick = { openScanner() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722), // Orange gradient color
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start, // Align items to the start
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.scan_btn),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.open_scan), fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(), // Make text fill remaining space
                    textAlign = TextAlign.Center // Center the text within the remaining space
                )
            }
        }

        // Second Button
        OutlinedButton(
            onClick = { /* Select Event */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            ),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(text = stringResource(R.string.choose_event), fontSize = 16.sp)
        }
    }
}

@Composable
fun EventCard(eventData: EventsResponse.Value) {
    // Карточка с информацией о событии
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .height(296.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Карточка с фоном
            Image(
                painter = rememberImagePainter(eventData.poster),
                contentDescription = "Event Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Градиентный оверлей
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x001F0056),
                                Color(0x99000000)
                            )
                        )
                    )
            )

            // Текст статуса события
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .background(
                        color = getStatusBackgroundColor(eventData.status),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = getStatusText(eventData.status),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
            }

            // Информация о событии
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = formatDate(eventData.startDate, eventData.timeShift.toDouble()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.auxiliary_20)
                )
                Text(
                    text = eventData.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun getStatusText(status: Int): String {
    return when (status) {
        0 -> stringResource(R.string.activno) // ACTIVE
        1 -> stringResource(R.string.zaplanirovano) // PLANNED
        2 -> stringResource(R.string.zaversheno) // COMPLETED
        3 -> stringResource(R.string.otmeneno) // CANCELED
        else -> "Неизвестно"
    }
}

@Composable
fun getStatusBackgroundColor(status: Int): Color {
    return when (status) {
        0 -> Color(0xFF28A745) // Зеленый для ACTIVE
        1 -> Color(0xFFFFC107) // Желтый для PLANNED
        2 -> Color(0xFF6C757D) // Серый для COMPLETED
        3 -> Color(0xFFDC3545) // Красный для CANCELED
        else -> Color(0xFF6C757D) // Default серый
    }
}

fun formatDate(startDate: String?, timeShift: Double): String {
    return if (!startDate.isNullOrEmpty() && timeShift != null) {
        try {
            val parsedDate = parseDateString(startDate)
            val shiftedDate = applyTimeShift(parsedDate, timeShift)
            formatDateString(shiftedDate!!)
        } catch (e: Exception) {
            "Invalid Date"
        }
    } else {
        "Unknown Date"
    }
}

@Composable
fun toolBar(onLogoutClick: () -> Unit){
    // Toolbar
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Main",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )
        IconButton(onClick = { onLogoutClick() },
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.exit), contentDescription = "Exit")
        }
    }
}

@Composable
fun LineWithDropShadow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .graphicsLayer { shadowElevation = 1f } // Устанавливаем тень
            .background(colorResource(R.color.auxiliary_20)) // Основная линия
    )
}
val robotoRegular = FontFamily(
    Font(R.font.roboto_regular)
)
@Composable
fun EmptyStateScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date Text
            Text(
                text = stringResource(id = R.string.not_search_active_event),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = robotoRegular,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Event Name Text
            Text(
                text = stringResource(id = R.string.please_wait_active_event),
                style = TextStyle(
                    color = colorResource(id = R.color.auxiliary_70),
                    fontSize = 16.sp,
                    fontFamily = robotoRegular
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Empty Events Image
            Image(
                painter = painterResource(id = R.drawable.empty_events_image),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}